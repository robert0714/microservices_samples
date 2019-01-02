package develop.ptx.bike.services;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import develop.ptx.bike.model.ArrayOfBikeStation;
import develop.ptx.bike.model.BikeStation;
import develop.ptx.bike.model.LocationParam;
import develop.ptx.bike.model.PTXBikeQueryParam;
import develop.ptx.common.services.CommonServices; 

/**
 * @HystrixCommand不能搭配有範型Generic Type的方法使用，會失效
 **/
@Component
public class BikeService extends CommonServices {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	/** The xml mapper. */
	private XmlMapper xmlMapper;

	/** The jackson mapper. */
	@Autowired
	private ObjectMapper mapper;
	
	/***
	 * server side 快取
	 */
    protected LoadingCache<String, List<BikeStation>> cacheData = CacheBuilder.newBuilder().maximumSize(1000)
			.expireAfterAccess(1, TimeUnit.DAYS).build(new CacheLoader<String, List<BikeStation>>() {
				@Override
				public List<BikeStation> load(final String city) throws Exception {
					return loadCityData(city);
				}
			});

	private String resourceUrl = "https://ptx.transportdata.tw/MOTC/v2/Bike/Station/";

	// 申請的APPID
	// （FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF 為 Guest 帳號，以IP作為API呼叫限制，請替換為註冊的APPID &
	// APPKey）
	protected String appID = "a5ce72c833f541d7bdb1a39b0ac80626";
	// 申請的APPKey
	protected String appKey = "gcQrWX5cQwGCJVPtIVrr4rskCew";

	@PostConstruct
	public void postConstruct() {

		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		this.xmlMapper = new XmlMapper(module);
		this.xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
//		this.mapper = new ObjectMapper();
//		this.mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}

	protected ArrayOfBikeStation buildFallbackPtxBike(PTXBikeQueryParam param) {
		return buildFallback(ArrayOfBikeStation.class);
	}

	@HystrixCommand(fallbackMethod = "buildFallbackPtxBike", threadPoolKey = "ptxBikeThreadPool", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "30"),
			@HystrixProperty(name = "maxQueueSize", value = "10") }, commandProperties = {
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
					@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5") })
	public ArrayOfBikeStation ptxBike(PTXBikeQueryParam param) {
		ArrayOfBikeStation result = new ArrayOfBikeStation();
		HttpHeaders headers = createHttpHeaders(appID, appKey);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		String resourceURL = resourceUrl + param.getCity() +"?$format=JSON";

		ResponseEntity<BikeStation[]> genernalResponse = restTemplate.exchange(resourceURL, HttpMethod.GET, entity,
				BikeStation[].class);

//		 ResponseEntity<String> response = restTemplate.exchange(resourceURL, HttpMethod.GET, entity, String.class);
//		 String json = response.getBody() ;
//		 System.out.println(json);;
//		BikeStation[] aPoint01Request = this.mapper.readValue(json, BikeStation[].class);
		result.setBikeStations(Arrays.asList(genernalResponse.getBody()));
		return result;
	}

	public ArrayOfBikeStation getPTXBikeAllStaticData(LocationParam location) {
		final ArrayOfBikeStation result = new ArrayOfBikeStation();
		final List<BikeStation> resultList = new ArrayList<>();
		final List<BikeStation> init = getPTXBikeAllStaticData();
		double leftLatitude = new BigDecimal(location.getLeftPosition().getLatitude()).doubleValue();
		double leftLonitude = new BigDecimal(location.getLeftPosition().getLontitude()).doubleValue();

		double rightLatitude = new BigDecimal(location.getRightPosition().getLatitude()).doubleValue();
		double rightLonitude = new BigDecimal(location.getRightPosition().getLontitude()).doubleValue();
		// 開始將資料進行篩選
		for (BikeStation station : init) {
			/**
			 * 緯度
			 */
			double latitude = new BigDecimal(station.getStationPosition().getLatitude()).doubleValue();
			/**
			 * 經度
			 */
			double lontitude = new BigDecimal(station.getStationPosition().getLontitude()).doubleValue();
			if (leftLatitude > latitude && latitude > rightLatitude && leftLonitude < lontitude
					&& lontitude < rightLonitude) {
				resultList.add(station);
			}
		}
		LOGGER.info("data size : {}",resultList.size());
		if(CollectionUtils.isNotEmpty(resultList)) {
			for(BikeStation station : resultList) {
				LOGGER.info("unit  data : {}",ToStringBuilder.reflectionToString(station));
			}
		}
		result.setBikeStations(resultList);
		return result;
	}

	protected List<BikeStation> loadCityData(final String city) {
		List<BikeStation> result = new ArrayList<>();
		PTXBikeQueryParam request = new PTXBikeQueryParam();
		request.setCity(city);
		final ArrayOfBikeStation arrayData = ptxBike(request);
		List<BikeStation> data1 = arrayData.getBikeStations();
		if (data1 != null) {
			result.addAll(data1);
		}
		return result;
	}

	protected List<BikeStation> getPTXBikeAllStaticData() {
		final List<BikeStation> result = new ArrayList<>();
//		result.addAll(loadCityData("Taipei"));
//		result.addAll(loadCityData("NewTaipei"));
		try {
			result.addAll(this.cacheData.get("Taipei"));
			result.addAll(this.cacheData.get("NewTaipei"));
		} catch (ExecutionException e) {
			LOGGER.error(e.getMessage() ,e);
			
		}
		
		return result;
	}
}
