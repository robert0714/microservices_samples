package develop.ptx.bike.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationParam {
	private Position leftPosition;
	private Position rightPosition;
	public Position getLeftPosition() {
		return leftPosition;
	}
	public void setLeftPosition(Position leftPosition) {
		this.leftPosition = leftPosition;
	}
	public Position getRightPosition() {
		return rightPosition;
	}
	public void setRightPosition(Position rightPosition) {
		this.rightPosition = rightPosition;
	}
	
}
