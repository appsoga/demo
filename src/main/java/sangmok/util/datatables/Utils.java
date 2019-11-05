package sangmok.util.datatables;

import java.util.ArrayList;
import java.util.List;

public class Utils {

  private static class DataTablesPageRequest extends org.springframework.data.domain.PageRequest {

    private static final long serialVersionUID = 1L;

    public DataTablesPageRequest(int page, int size, org.springframework.data.domain.Sort sort) {
      super(page, size, sort);
    }

  }

  public final static String OR_SEPARATOR = "+";
  public final static String ESCAPED_OR_SEPARATOR = "\\+";
  public final static String ATTRIBUTE_SEPARATOR = ".";
  public final static String ESCAPED_ATTRIBUTE_SEPARATOR = "\\.";

  public final static char ESCAPE_CHAR = '\\';

  public static String getLikeFilterValue(String filterValue) {
    return "%" + filterValue.toLowerCase().replaceAll("%", "\\\\" + "%").replaceAll("_", "\\\\" + "_") + "%";
  }

  /**
   * Creates a 'LIMIT .. OFFSET .. ORDER BY ..' clause for the given
   * {@link DataTablesInput}.
   * 
   * @param request the {@link DataTablesInput} mapped from the Ajax request
   * @return a {@link Pageable}, must not be {@literal null}.
   */
  public static org.springframework.data.domain.Pageable getPageable(DataTablesRequest request) {
    List<org.springframework.data.domain.Sort.Order> orders = new ArrayList<org.springframework.data.domain.Sort.Order>();
    for (Order order : request.getOrder()) {
      Column column = request.getColumns().get(order.getColumn());
      if (column.getOrderable()) {
        String sortColumn = column.getData();
        org.springframework.data.domain.Sort.Direction sortDirection = org.springframework.data.domain.Sort.Direction
            .fromString(order.getDir());
        orders.add(new org.springframework.data.domain.Sort.Order(sortDirection, sortColumn));
      }
    }

    org.springframework.data.domain.Sort sort = orders.isEmpty() ? org.springframework.data.domain.Sort.unsorted()
        : org.springframework.data.domain.Sort.by(orders);

    if (request.getLength() == -1) {
      request.setStart(0);
      request.setLength(Integer.MAX_VALUE);
    }
    return new DataTablesPageRequest(request.getPage(), request.getSize(), sort);
  }

  public static boolean isBoolean(String filterValue) {
    return "TRUE".equalsIgnoreCase(filterValue) || "FALSE".equalsIgnoreCase(filterValue);
  }

}
