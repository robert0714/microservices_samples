package develop.ptx.bike.model;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import develop.ptx.bike.model.BikeStation; 
import develop.ptx.bike.model.ArrayOfBikeStation; 
 
 
 
public class ArrayOfBikeStationTest {
	  
   
   /** The xml mapper. */
   private    XmlMapper xmlMapper ; 
	 
   
   /** The jackson mapper. */
   private    ObjectMapper mapper ;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {  
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false); 
		this.xmlMapper = new XmlMapper(module);
		this.xmlMapper.configure( ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false );  
		mapper = new ObjectMapper();
		 
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
	}


	
 
	@Test
//	@Ignore
	public void testPoint01Request() throws Exception {
		ArrayOfBikeStation request =new ArrayOfBikeStation();
		BikeStation request00=new BikeStation();
		request00.setAuthorityID("1");;
		request00.setBikesCapacity(1);;		
		request00.setUpdateTime(new Date());
		StationAddress stationAddress=new StationAddress();
		stationAddress.setEn("Minzu & Yanping Intersection");
		stationAddress.setZhTw("民族延平路口");;
		
		request00.setStationAddress(stationAddress);
		
		
		List<BikeStation> bikeStations= new ArrayList<>();
		bikeStations.add(request00);
		request.setBikeStations(bikeStations);;
		 
		System.out.println(	this.mapper.writeValueAsString(request));
	
		
		String resource = "/response.json";
		String json = new String(Files.readAllBytes(Paths.get(getClass().getResource(resource).toURI())),StandardCharsets.UTF_8);
		 
		
		
		System.out.println(json);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		BikeStation[] aPoint01Request = this.mapper.readValue(json, BikeStation[].class);
		
		 
		System.out.println(aPoint01Request); 
	} 
	 
	@Test
	@Ignore
	public void testPoint02Request() throws Exception {
		ArrayOfBikeStation request =new ArrayOfBikeStation();
		BikeStation request00=new BikeStation();
		request00.setAuthorityID("1");;
		request00.setBikesCapacity(1);;		
		request00.setUpdateTime(new Date());
		StationAddress stationAddress=new StationAddress();
		stationAddress.setEn("Minzu & Yanping Intersection");
		stationAddress.setZhTw("民族延平路口");;
		
		request00.setStationAddress(stationAddress);
		
		
		List<BikeStation> bikeStations= new ArrayList<>();
		bikeStations.add(request00);
		request.setBikeStations(bikeStations);;
		 
		System.out.println(	this.xmlMapper.writeValueAsString(request));
	
		
		String resource = "/response.xml";
		String xml = new String(Files.readAllBytes(Paths.get(getClass().getResource(resource).toURI())),StandardCharsets.UTF_8);
		
		String replaceText =StringEscapeUtils.escapeHtml3("&");
		
		String data = xml.replace("&", replaceText);
		
		
		System.out.println(data);
		 
		ArrayOfBikeStation aPoint01Request = this.xmlMapper.readValue(data, ArrayOfBikeStation.class);
		
		 
		System.out.println(aPoint01Request); 
	} 
	 
	 
	 
}
