package develop.ptx.bike.endpoint;

import static org.junit.Assert.assertNotNull;
 
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner; 
 
import develop.ptx.Application;
import develop.ptx.bike.model.ArrayOfBikeStation;
import develop.ptx.bike.model.PTXBikeQueryParam; 

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes =  Application.class)
@DirtiesContext
public class BikeEndpointTest {
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(BikeEndpointTest.class);

	@Before
	public void setUp() throws Exception { 
	}
	/** The rest template. */
	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	@Ignore
	public void testP02() throws Exception {
		PTXBikeQueryParam param =new PTXBikeQueryParam();
		param.setCity("Taipei");
		
		String uri = "/v1/ptx/bike/static";
		ArrayOfBikeStation response  = restTemplate.postForObject(uri, param, ArrayOfBikeStation.class);  
		 
		assertNotNull(response);;
		 
	}
}
