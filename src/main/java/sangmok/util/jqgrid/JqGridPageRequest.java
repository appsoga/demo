package sangmok.util.jqgrid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class JqGridPageRequest extends PageRequest {

	public JqGridPageRequest(int page, int size, Sort sort) {
		super(page, size, sort);
	}

	public JqGridPageRequest(JqGridRequest jr) {
		super(jr.getPage() - 1, jr.getRows(), jr.getSort());
	}

	private static final long serialVersionUID = 7579319938097972891L;

}
