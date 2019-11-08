package sangmok.util.jtable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class JTablesPageRequest extends PageRequest {

	public JTablesPageRequest(int page, int size, Sort sort) {
		super(page, size, sort);
	}

	private static final long serialVersionUID = 8140653647991940999L;

}
