package sangmok.util.jsgrid;

import java.util.List;

@lombok.Data
public class JsGridResponse<T> {
	private List<T> data;
	private Long itemsCount;
}