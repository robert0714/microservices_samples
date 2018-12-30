package develop.ptx.bike.model;

public class PTXBikeQueryParam {
	/**
	 * 
	 * **/
	private String city;
	/**
	 * 可填入欲撈取欄位資料之名稱如 StationUID
	 * **/
	private String select;
	/**
	 * 
	 * 可填入欄位值篩選資料 如StationUID eq ‘NWT1302’
	 * 
	 * 
	 **/
	private String filter;
	/**
	 * 取前幾筆之筆數，如 30
	 * **/
	private String top;
	/**
	 * 
	 * 忽略前幾筆之筆數，如30
	 * 
	 **/
	private String skip;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getSkip() {
		return skip;
	}

	public void setSkip(String skip) {
		this.skip = skip;
	}

}
