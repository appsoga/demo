package sangmok.util.jsgrid;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@lombok.Data
public class JsGridRequest {
	private Integer pageIndex = 0;
	private Integer pageSize = 10;
	private String sortField;
	private String sortOrder;

	public Sort getSort() {
		if (sortField == null)
			return Sort.unsorted();
		if (sortOrder == null)
			return Sort.unsorted();

		Direction direction = (sortOrder.equalsIgnoreCase("ASC")) ? Direction.ASC : Direction.DESC;
		// return Sort.
		return Sort.by(direction, sortField);
	}
}