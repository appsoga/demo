package sangmok.util.datatables;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonView;

import sangmok.util.JsonViewScope;

public class DataTablesResponse<T> {

	private Logger logger = LoggerFactory.getLogger(DataTablesResponse.class);
	// @JsonView(JsonViewScope.Data.class)
	private int draw;
	// @JsonView(JsonViewScope.Data.class)
	private long recordsFiltered;
	// @JsonView(JsonViewScope.Data.class)
	private long recordsTotal;
	// @JsonView(JsonViewScope.Data.class)
	private List<Object> data;
	// @JsonView(JsonViewScope.Data.class)
	private DataTablesRequest command;

	public DataTablesResponse(DataTablesRequest command, org.springframework.data.domain.Page<T> page, Class<?> wrapperClazz)
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		logger.debug("{}", page);
		this.draw = command.getDraw() + 1;
		this.recordsFiltered = page.getTotalElements();
		this.recordsTotal = page.getTotalElements();
		this.command = command;
		if (page.getNumberOfElements() > 0) {
			data = new ArrayList<Object>();
			for (T o : page.getContent()) {
				data.add(newInstance(wrapperClazz, o));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public DataTablesResponse(DataTablesRequest command, org.springframework.data.domain.Page<T> page) {
		logger.debug("{}", page);
		this.draw = command.getDraw() + 1;
		this.recordsFiltered = page.getTotalElements();
		this.recordsTotal = page.getTotalElements();
		this.command = command;
		this.data = (List<Object>) page.getContent();
	}
	
	public int getDraw() { return draw;	}
	public long getRecordsFiltered() { return recordsFiltered; }
	public long getRecordsTotal() { return recordsTotal; }
	public List<Object> getData() { return data; }
	public DataTablesRequest getCommand() { return command; }

	private Object newInstance(Class<?> wrapperClazz, T o)
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Class<?> paramClazz = o.getClass();		
		Constructor<?> constructor = wrapperClazz.getConstructor(paramClazz);
		Object wrapperObject = constructor.newInstance(o);		
		return wrapperObject;
	}

}
