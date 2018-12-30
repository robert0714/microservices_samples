package develop.ptx.bike.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "StationPosition") 
public class Position {
	/**
	 * 緯度
	 * */
	@JacksonXmlProperty(localName = "PositionLat")
	@JsonProperty( "PositionLat")
	private String latitude;
	/**
	 * 經度
	 * */
	@JacksonXmlProperty(localName = "PositionLon")
	@JsonProperty( "PositionLon")
	private String lontitude;
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLontitude() {
		return lontitude;
	}
	public void setLontitude(String lontitude) {
		this.lontitude = lontitude;
	}
	 
	
}
