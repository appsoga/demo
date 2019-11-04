package sangmok.util.datatables;

import java.util.ArrayList;
import java.util.List;

public class DataTablesRequest {
	
	private int draw;
	private int start;
	private int length;
	private List<Column> columns;
	private List<Order> order;
	private Search search;
	
	{
		this.draw = 1;
		this.start = 0;
		this.length = 15;
		this.columns =new ArrayList<Column>();
		this.order = new ArrayList<Order>();
		this.search = new Search();
	}
	
	/**
	 * for spring JPA repository
	 * ********************************************************/	
	public int getPage() { return (int)(start / length); }
	public int getSize() {	return length; }
	public org.springframework.data.domain.Pageable getPageable() { return Utils.getPageable(this); }

	/********************************************************/
	
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<Order> getOrder() {
		return order;
	}
	public void setOrder(List<Order> order) {
		this.order = order;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	@Override
	public String toString() {
		return "DataTableRequest [draw=" + draw + ", start=" + start + ", length=" + length + ", columns=" + columns
				+ ", order=" + order + ", search=" + search + "]";
	}
	


}
