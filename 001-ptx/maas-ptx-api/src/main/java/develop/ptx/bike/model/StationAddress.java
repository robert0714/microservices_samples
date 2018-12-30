package develop.ptx.bike.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "StationAddress") 
public class StationAddress {
	/**
	 * 中文地址
	 * */
	@JacksonXmlProperty(localName = "Zh_tw")
	@JsonProperty( "Zh_tw")
	private String zhTw;
	/**
	 * 英文地址
	 * */
	@JacksonXmlProperty(localName = "En")
	@JsonProperty( "En")
	private String en;
	public String getZhTw() {
		return zhTw;
	}
	public void setZhTw(String zhTw) {
		this.zhTw = zhTw;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
}
