package develop.ptx.bike.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/***
 * 查詢客戶歡樂點數
 **/
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "ArrayOfBikeStation" )
public class ArrayOfBikeStation extends CommonResponse{
 
	@JacksonXmlElementWrapper(localName = "BikeStation", useWrapping = false)
	@JacksonXmlProperty(localName = "BikeStation")
	@JsonProperty( "BikeStation")
	private List<BikeStation> bikeStations;

	public List<BikeStation> getBikeStations() {
		return bikeStations;
	}

	public void setBikeStations(List<BikeStation> bikeStations) {
		this.bikeStations = bikeStations;
	}

}
