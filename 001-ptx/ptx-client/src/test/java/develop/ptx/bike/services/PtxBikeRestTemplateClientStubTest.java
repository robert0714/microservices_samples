package develop.ptx.bike.services;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.startsWith;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment; 
import org.springframework.test.context.junit4.SpringRunner; 
 

import develop.ptx.TestApplication; 
import develop.ptx.bike.model.ArrayOfBikeStation;
import develop.ptx.bike.model.BikeStation;
import develop.ptx.bike.model.LocationParam;
import develop.ptx.bike.model.PTXBikeQueryParam; 
import io.specto.hoverfly.junit.rule.HoverflyRule;

@RunWith(SpringRunner.class) 
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = TestApplication.class)
public class PtxBikeRestTemplateClientStubTest {
	
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PtxBikeRestTemplateClientStubTest.class);
    private static String staticRepsonseContentJson = null;
    static {
		try {
			staticRepsonseContentJson = new String(
					Files.readAllBytes(Paths.get(PtxBikeRestTemplateClientStubTest.class.getResource("ptx_static.json").toURI())),
					StandardCharsets.UTF_8);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	 
	@ClassRule
	public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(dsl(
			service("ptx-service").andDelay(200, TimeUnit.MILLISECONDS).forAll().anyMethod(startsWith("/v1/ptx/bike/scope")).anyBody()
					.willReturn(success(staticRepsonseContentJson, "application/json"))))
			.printSimulationData();
	
	
	
	@Autowired
	private PtxBikeRestTemplateClient ptxBikeRestTemplateClientStub;

	@Before
	public void setUp() throws Exception { 
	}

	@After
	public void tearDown() throws Exception { 
	}

	@Test 
	public void testGetPTXBikeAllStaticData() throws Exception {
		List<BikeStation> data = ptxBikeRestTemplateClientStub.getPTXBikeAllStaticData();
		Assert.assertNotNull(data);
	}

	@Test	
	public void testPtxBikeStatic() throws Exception {
		PTXBikeQueryParam request = new PTXBikeQueryParam();
		request.setCity("Taipei");
		final ArrayOfBikeStation arrayData = ptxBikeRestTemplateClientStub.ptxBikeStatic(request);
		Assert.assertNotNull(arrayData);
	}
	 
    

	@Test 
	public void testGetPTXBikeAllStaticDataByLocation() throws Exception {
		LocationParam location   = new LocationParam();
		 
		ArrayOfBikeStation result = ptxBikeRestTemplateClientStub.getPTXBikeAllStaticDataByLocation(location);
		Assert.assertEquals(122, result.getBikeStations().size());
	}



}
