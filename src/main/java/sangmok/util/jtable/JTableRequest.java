package sangmok.util.jtable;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@lombok.Data
public class JTableRequest {

//	@JsonProperty(value = "jtStartIndex")
	private Integer jtStartIndex = 0;

//	@JsonProperty(value = "jtPageSize")
	private Integer jtPageSize = 10;

//	@JsonProperty("jtSorting")	
	private String jtSorting;

	private Integer page = 0;

	public void setJtStartIndex(Integer v) {
		this.jtStartIndex = v;
		this.page = (jtStartIndex == 0 ? 0 : jtStartIndex / jtPageSize);
	}

	public Sort getSort() {
		if (jtSorting == null)
			return Sort.unsorted();
		if (jtSorting.length() < 4)
			return Sort.unsorted();

		String[] token = jtSorting.split(" ");
		if (token.length < 2)
			return Sort.unsorted();

		String property = token[0];
		Direction direction = (token[1].equalsIgnoreCase("ASC")) ? Direction.ASC : Direction.DESC;
		// return Sort.
		return Sort.by(direction, property);
	}
}
