package sangmok.util.jqgrid;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class JqGridResponse<T> {

	public JqGridResponse(Page<T> page) {
		this.data = page.getContent();
		this.totalPageSize = page.getTotalPages();
		this.currentPage = page.getNumber() + 1;
		this.totalRecords = page.getTotalElements();
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("records")
	private Long totalRecords;
	@JsonProperty("page")
	private Integer currentPage;
	@JsonProperty("total")
	private Integer totalPageSize;
	@JsonProperty("rows")
	private List<T> data;
}
