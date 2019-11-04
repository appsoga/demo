package sangmok.util.datatables;

import javax.validation.constraints.NotNull;

public class Search {

	/**
	 * Global search value. To be applied to all columns which have searchable
	 * as true.
	 */
	@NotNull
	private String value;

	/**
	 * true if the global filter should be treated as a regular expression for
	 * advanced searching, false otherwise. Note that normally server-side
	 * processing scripts will not perform regular expression searching for
	 * performance reasons on large data sets, but it is technically possible
	 * and at the discretion of your script.
	 */
	@NotNull
	private Boolean regex;

	public Search() {
		super();
		this.value = "";
	}

	public Search(String value, Boolean regex) {
		super();
		this.value = value;
		this.regex = regex;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getRegex() {
		return regex;
	}

	public void setRegex(Boolean regex) {
		this.regex = regex;
	}

	@Override
	public String toString() {
		return "Search [value=" + value + ", regex=" + regex + "]";
	}

}