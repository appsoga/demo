package sangmok.util.charjs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonInclude(Include.NON_NULL)
public @lombok.Data class BarChart {

	private String type = "bar";
	
	private Data data;
	
	@JsonRawValue
	private String options;

}
