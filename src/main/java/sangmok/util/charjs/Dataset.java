package sangmok.util.charjs;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
public class Dataset<T> {

	private String type;
	private String label;
	private List<T> data = new ArrayList<T>();

	private String borderColor;
	private String backgroundColor;

	private String fillColor;
	private String strokeColor;

}