package develop.ptx.bike.services;
 
import java.util.List;

import develop.ptx.bike.model.ArrayOfBikeStation;
import develop.ptx.bike.model.BikeStation;
import develop.ptx.bike.model.LocationParam;
import develop.ptx.bike.model.PTXBikeQueryParam;
import develop.ptx.exception.PTXConnectionFailedException;

/**
 * @author robert0714
 *
 */
public interface PtxBikeRestTemplateClient {
	/***
	 * 取得指定[縣市]的公共自行車租借站位資料 (靜態)<br/>
	 * 台北市: {City} = Taipei <br/>
	 * 新北市: {City} = NewTaipei
	 * 
	 **/
	public ArrayOfBikeStation ptxBikeStatic(PTXBikeQueryParam request01)throws PTXConnectionFailedException;
	
	/***
	 * 取得台灣全部公共自行車租借站位資料 (靜態)<br/>
	 * 
	 **/
	public List<BikeStation>  getPTXBikeAllStaticData() throws PTXConnectionFailedException;

	/***
	 * 從兩個經緯度位置取得此範圍公共自行車租借站位資料 (靜態)<br/>
	 * 
	 **/
	public List<BikeStation>  getPTXBikeAllStaticData(final LocationParam location) throws PTXConnectionFailedException;  
	
	
	ArrayOfBikeStation getPTXBikeAllStaticDataByLocation(final LocationParam location) ;
}
