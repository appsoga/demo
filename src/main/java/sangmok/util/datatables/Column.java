package sangmok.util.datatables;

import javax.validation.constraints.NotNull;

public class Column {

	/**
	 * Column's data source
	 */
	private String data;

	/**
	 * Column's name
	 */
	private String name;

	/**
	 * Flag to indicate if this column is searchable (true) or not (false).
	 */
	@NotNull
	private Boolean searchable;

	/**
	 * Flag to indicate if this column is orderable (true) or not (false).
	 */
	@NotNull
	private Boolean orderable;

	/**
	 * Search value to apply to this specific column.
	 */
	@NotNull
	private Search search;

	public Column() {
		super();
	}

	public Column(String name, String data, Boolean searchable, Boolean orderable, Search search) {
		super();
		this.name = name;
		this.data = data;
		this.searchable = searchable;
		this.orderable = orderable;
		this.search = search;
	}

	public String getData() {
		return data;
	}

	public String getName() {
		return name;
	}

	public Boolean getOrderable() {
		return orderable;
	}

	public Search getSearch() {
		return search;
	}

	public Boolean getSearchable() {
		return searchable;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrderable(Boolean orderable) {
		this.orderable = orderable;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}

	/**
	 * Set the search value to apply to this column
	 *
	 * @param searchValue
	 *            if any, the search value to apply
	 */
	public void setSearchValue(String searchValue) {
		this.search.setValue(searchValue);
	}

	@Override
	public String toString() {
		return "Column [data=" + data + ", name=" + name + ", searchable=" + searchable + ", orderable="
				+ orderable + ", search=" + search + "]";
	}

}