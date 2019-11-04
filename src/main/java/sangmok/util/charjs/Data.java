package sangmok.util.charjs;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
public class Data {
	private List<String> labels = new ArrayList<String>();
	private List<Dataset<?>> datasets = new ArrayList<Dataset<?>>();
}
