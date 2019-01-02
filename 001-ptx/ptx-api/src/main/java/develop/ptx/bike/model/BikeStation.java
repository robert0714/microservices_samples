package develop.ptx.bike.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "BikeStation") 
public class BikeStation  {

	/**
	 *站點唯一識別代碼，規則為 {業管機關代碼} + {StationID}，其中 {業管機關代碼} 可於Authority API中的AuthorityCode欄位查詢
	 * */
	@JacksonXmlProperty(localName = "StationUID")
	@JsonProperty( "StationUID")
	private String stationUID;

	/**
	 * 站點代碼
	 * */
	@JacksonXmlProperty(localName = "StationID")
	@JsonProperty( "StationID")
	private String stationID;

	/**
	 * 業管單位代碼
	 * */
	@JacksonXmlProperty(localName = "AuthorityID")
	@JsonProperty( "AuthorityID")
	private String authorityID;

	/**
	 * 站點名稱
	 * */
	@JacksonXmlProperty(localName = "StationName")
	@JsonProperty( "StationName")
	private StationName stationName;

	
	/**
	 * 站點位置
	 * */
	@JacksonXmlProperty(localName = "StationPosition")
	@JsonProperty( "StationPosition")
	private Position  stationPosition;
	
	/**
	 * 站點地址
	 * */
	@JacksonXmlProperty(localName = "StationAddress")
	@JsonProperty( "StationAddress")
	private StationAddress stationAddress; 
	
	
	/**
	 * 站點描述

	 * */
	@JacksonXmlProperty(localName = "StopDescription")
	@JsonProperty( "StopDescription")
	private String stopDescription ;
	
	
	/**
	 * 可容納之自行車總數
	 * */
	@JacksonXmlProperty(localName = "BikesCapacity")
	@JsonProperty( "BikesCapacity")
	private Integer bikesCapacity ;
	
	/**
	 * 來源端平台資料更新時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)
	 * */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", locale = "zh", timezone = "GMT+8")
	@JacksonXmlProperty(localName = "SrcUpdateTime")
	@JsonProperty( "SrcUpdateTime")
	private Date srcUpdateTime ;
	
	/**
	 * 資料更新日期時間(ISO8601格式:yyyy-MM-ddTHH:mm:sszzz)
	 * */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", locale = "zh", timezone = "GMT+8")
	@JacksonXmlProperty(localName = "UpdateTime")
	@JsonProperty( "UpdateTime")
	private Date updateTime ;

	public String getStationUID() {
		return stationUID;
	}

	public void setStationUID(String stationUID) {
		this.stationUID = stationUID;
	}

	public String getStationID() {
		return stationID;
	}

	public void setStationID(String stationID) {
		this.stationID = stationID;
	}

	public String getAuthorityID() {
		return authorityID;
	}

	public void setAuthorityID(String authorityID) {
		this.authorityID = authorityID;
	}

	public StationName getStationName() {
		return stationName;
	}

	public void setStationName(StationName stationName) {
		this.stationName = stationName;
	}

	public StationAddress getStationAddress() {
		return stationAddress;
	}

	public void setStationAddress(StationAddress stationAddress) {
		this.stationAddress = stationAddress;
	}

	public String getStopDescription() {
		return stopDescription;
	}

	public void setStopDescription(String stopDescription) {
		this.stopDescription = stopDescription;
	}

	public Integer getBikesCapacity() {
		return bikesCapacity;
	}

	public void setBikesCapacity(Integer bikesCapacity) {
		this.bikesCapacity = bikesCapacity;
	}

	public Date getSrcUpdateTime() {
		return srcUpdateTime;
	}

	public void setSrcUpdateTime(Date srcUpdateTime) {
		this.srcUpdateTime = srcUpdateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Position getStationPosition() {
		return stationPosition;
	}

	public void setStationPosition(Position stationPosition) {
		this.stationPosition = stationPosition;
	}
 
	
}
