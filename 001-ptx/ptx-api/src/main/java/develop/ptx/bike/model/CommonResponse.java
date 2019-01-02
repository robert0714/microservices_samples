package develop.ptx.bike.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResponse {
	private Error error;

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
}
