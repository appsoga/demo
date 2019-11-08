package sangmok.util.jqgrid;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class JqGridRequest {
	private Integer q;
	@JsonProperty("page")
	private Integer page = 1;
	@JsonProperty("rows")
	private Integer rows = 10;
	@JsonProperty("sidx")
	private String sidx;
	@JsonProperty("sord")
	private String sord;

	@JsonProperty("_search")
	private Boolean search;

	private Long nd;

	public Sort getSort() {
		if (sidx == null || sidx.isEmpty())
			return Sort.unsorted();
		if (sord == null || sord.isEmpty())
			return Sort.unsorted();

		Direction direction = (sord.equalsIgnoreCase("ASC")) ? Direction.ASC : Direction.DESC;
		// return Sort.
		return Sort.by(direction, sidx);
	}
}
