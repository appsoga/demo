package sangmok.util.jsgrid;

import java.lang.reflect.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.demo.data.GlobalEnum;

public class JsGridSpecificationFactory {

	private static java.util.List<Class<?>> equalTypes;

	static {
		equalTypes = new java.util.ArrayList<Class<?>>();
		equalTypes.add(java.lang.Boolean.class);
		equalTypes.add(java.lang.Integer.class);
		equalTypes.add(java.lang.Long.class);
		equalTypes.add(java.lang.Double.class);
		equalTypes.add(java.math.BigDecimal.class);
		equalTypes.add(java.math.BigInteger.class);
	}

	public static <T> org.springframework.data.jpa.domain.Specification<T> toSpecification(final Object obj) {
		return new JsSpecification<T>(obj);
	}

	public static class JsSpecification<T> implements org.springframework.data.jpa.domain.Specification<T> {

		private static final long serialVersionUID = 1L;
		private Object obj;

		public JsSpecification(Object obj) {
			this.obj = obj;
		}

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

			Predicate predicate = cb.conjunction();

			for (Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				try {
					String fname = f.getName();
					Object val = f.get(obj);
					if (val == null)
						continue;

					if (equalTypes.contains(f.getType()) || val instanceof java.lang.Enum) {
						Predicate p = cb.equal(root.get(fname), val);
						predicate = cb.and(predicate, p);
					} else {
						if (val.toString().isEmpty())
							continue;
						Predicate p = cb.like(root.get(fname), String.format("%%%s%%", val));
						predicate = cb.and(predicate, p);
					}

				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return predicate;
		}

	}

}