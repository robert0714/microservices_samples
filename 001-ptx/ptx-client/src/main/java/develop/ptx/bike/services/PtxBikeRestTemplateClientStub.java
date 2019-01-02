package develop.ptx.bike.services;
 
import java.util.ArrayList;
import java.util.List; 

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper; 
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import develop.ptx.bike.model.ArrayOfBikeStation;
import develop.ptx.bike.model.BikeStation;
import develop.ptx.bike.model.CommonResponse;
import develop.ptx.bike.model.LocationParam;
import develop.ptx.bike.model.PTXBikeQueryParam;
import develop.ptx.exception.PTXConnectionFailedException;

@Component
public class PtxBikeRestTemplateClientStub implements PtxBikeRestTemplateClient {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private RestTemplate restTemplate;

	/** The jackson mapper. */
	@Autowired
	private ObjectMapper mapper;

	private final String serviceId = "ptx-service";

	protected List<BikeStation> loadCityData(final String city) throws PTXConnectionFailedException {
		List<BikeStation> result = new ArrayList<>();
		PTXBikeQueryParam request = new PTXBikeQueryParam();
		request.setCity(city);
		final ArrayOfBikeStation arrayData = ptxBikeStatic(request);
		List<BikeStation> data1 = arrayData.getBikeStations();
		if (data1 != null) {
			result.addAll(data1);
		}
		return result;
	}
	@HystrixCommand(fallbackMethod = "buildFallbackPTXBikeStatic", threadPoolKey = "ptxBikeStaticThreadPool", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "30"),
			@HystrixProperty(name = "maxQueueSize", value = "10") }, commandProperties = {
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
					@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5") })
	protected ArrayOfBikeStation ptxBikeStaticHystrix(final PTXBikeQueryParam request) {
		LOGGER.debug("starting remote retrieveing data ");
		String serviceUri = String.format("http://%s/v1/ptx/bike/static", serviceId);

		ResponseEntity<ArrayOfBikeStation> restExchange = restTemplate.postForEntity(serviceUri, request,
				ArrayOfBikeStation.class);

		LOGGER.debug("remote retrieveing data sucessful ");
		return restExchange.getBody();
	}

	/***
	 * HystrixCommand的fallbackMethod方法
	 */
	protected ArrayOfBikeStation buildFallbackPTXBikeStatic(final PTXBikeQueryParam request) {
		LOGGER.error("buildFallbackPTXBikeStatic");
		ArrayOfBikeStation result = new ArrayOfBikeStation();
		return result;
	}

	protected void isPTXConnectionFailedException(final CommonResponse response) throws PTXConnectionFailedException {
		if (response != null && response.getError() != null && StringUtils.isNotEmpty(response.getError().getMsg())) {
			LOGGER.debug("service ({})   trows   HamiPointConnectionFailedException ", serviceId);
			throw new PTXConnectionFailedException(response.getError().getMsg());
		}
	}

	@Override
	public ArrayOfBikeStation ptxBikeStatic(PTXBikeQueryParam request) throws PTXConnectionFailedException {
		ArrayOfBikeStation result = null;

		List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
		LOGGER.debug("service ({})  instance: {} ", serviceId, instances.size());
		if (instances.size() > 0) {
			result = ptxBikeStaticHystrix(request);
		} else {
			result = buildFallbackPTXBikeStatic(request);
		}
		isPTXConnectionFailedException(result);
		return result;
	}

	@Override
	public List<BikeStation> getPTXBikeAllStaticData() throws PTXConnectionFailedException {
		final List<BikeStation> result = new ArrayList<>();
		try { 
			result.addAll(loadCityData("Taipei"));
			result.addAll(loadCityData("NewTaipei"));
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
		}

		return result;
	}

	@Override
	public List<BikeStation> getPTXBikeAllStaticData(LocationParam location) throws PTXConnectionFailedException {
		final List<BikeStation> result = new ArrayList<>();
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
		LOGGER.debug("service ({})  instance: {} ", serviceId, instances.size());
		ArrayOfBikeStation data = null;
		if (instances.size() > 0) {
			data = getPTXBikeAllStaticDataByLocation(location);

		} else {
			data = buildFallbackPTXBikeStatic(location);
		}
		isPTXConnectionFailedException(data);
		
		if(data !=null && data.getBikeStations()!=null ) {
			result.addAll(data.getBikeStations());
		}
		
		return result;
	}

	/***
	 * HystrixCommand的fallbackMethod方法
	 */
	protected ArrayOfBikeStation buildFallbackPTXBikeStatic(final LocationParam location) {
		LOGGER.error("buildFallbackPTXBikeStatic");
		ArrayOfBikeStation result = new ArrayOfBikeStation();
		return result;
	}

	@HystrixCommand(fallbackMethod = "buildFallbackPTXBikeStatic", threadPoolKey = "ptxBikeStaticThreadPool", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "30"),
			@HystrixProperty(name = "maxQueueSize", value = "10") }, commandProperties = {
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
					@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5") })
	public ArrayOfBikeStation getPTXBikeAllStaticDataByLocation(final LocationParam location) {
		LOGGER.debug("starting remote retrieveing data ");
		String serviceUri = String.format("http://%s/v1/ptx/bike/scope", serviceId);

		
		 String  json = restTemplate.postForObject(serviceUri, location,
				String.class);
		 
		System.out.println(json);

		ResponseEntity<ArrayOfBikeStation> restExchange = restTemplate.postForEntity(serviceUri, location,
				ArrayOfBikeStation.class);

		LOGGER.debug("remote retrieveing data sucessful ");
		return restExchange.getBody();
	}
}
