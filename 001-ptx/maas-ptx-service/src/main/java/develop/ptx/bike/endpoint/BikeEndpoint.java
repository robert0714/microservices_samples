package develop.ptx.bike.endpoint;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.MediaType; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController;

import develop.ptx.bike.model.ArrayOfBikeStation;
import develop.ptx.bike.model.BikeStation;
import develop.ptx.bike.model.LocationParam;
import develop.ptx.bike.model.PTXBikeQueryParam;
import develop.ptx.bike.services.BikeService;

@RestController
@RequestMapping(value = "/v1/ptx/bike")
public class BikeEndpoint {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BikeService service;

	@RequestMapping(value = { "/static" }, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ArrayOfBikeStation ptxBikeStatic(@RequestBody PTXBikeQueryParam request) {
		LOGGER.debug("ptxBikeStatic");
		return service.ptxBike(request);
	}
	
	@RequestMapping(value = { "/scope" }, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ArrayOfBikeStation getPTXBikeAllStaticData(@RequestBody LocationParam location) {		 
		LOGGER.debug("getPTXBikeAllStaticData") ;
		return service.getPTXBikeAllStaticData(location);
	}
	
}
