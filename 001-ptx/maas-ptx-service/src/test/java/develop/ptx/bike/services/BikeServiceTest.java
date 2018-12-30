package develop.ptx.bike.services;
 
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import develop.ptx.Application;
import develop.ptx.bike.model.ArrayOfBikeStation;
import develop.ptx.bike.model.BikeStation;
import develop.ptx.bike.model.LocationParam;
import develop.ptx.bike.model.PTXBikeQueryParam;
import develop.ptx.bike.model.Position;
import develop.ptx.bike.services.BikeService; 

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes =  Application.class)
@DirtiesContext
public class BikeServiceTest {

	@Autowired
	private BikeService bikeService ;
	
	 

	@Test
	@Ignore
	public void testPtxBike() throws Exception {
		PTXBikeQueryParam param =new PTXBikeQueryParam();
		param.setCity("Taipei");
		ArrayOfBikeStation result = bikeService.ptxBike(param);
		assertNotNull(result);
		
	}
	@Test
	@Ignore
	public void testGetPTXBikeAllStaticDataLocationParam() throws Exception {
		
		LocationParam location= new LocationParam();
		//左上右下資料
		
		Position leftPosition=new Position();
		leftPosition.setLatitude("25.030143351725634");
		leftPosition.setLontitude("121.43400734384625");
		
		location.setLeftPosition(leftPosition);

		Position rightPosition=new Position();
		rightPosition.setLatitude("24.969000972225047");
		rightPosition.setLontitude("121.50278857071964");
		location.setRightPosition(rightPosition);
		ArrayOfBikeStation data = bikeService.getPTXBikeAllStaticData(location);
		
		
		 ObjectMapper mapper =new ObjectMapper();
		 String json = mapper.writeValueAsString(data);
		 
		 System.out.println("-------------------");;
		 System.out.println(json);;
		 System.out.println("-------------------");;
		List<BikeStation> bikes = data.getBikeStations();
		assertEquals(122, bikes.size());
		
	}
}
