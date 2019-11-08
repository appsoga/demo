package sangmok.util.jtable;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class JTablePageResponse<T> {
	public JTablePageResponse(Page<T> page) {
		result = "OK";
		records = page.getContent();
	}

	public JTablePageResponse(T obj) {
		result = "OK";
		record = obj;
	}

	public JTablePageResponse() {
	}

	@JsonProperty("Result")
	private String result = "OK"; // "ERROR"
	@JsonProperty("Message")
	private String message;
	@JsonProperty("Records")
	private List<T> records;
	@JsonProperty("Record")
	private T record;
}
