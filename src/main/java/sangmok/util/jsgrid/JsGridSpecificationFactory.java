package sangmok.util.jsgrid;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class JsGridSpecificationFactory {

	public static <T> org.springframework.data.jpa.domain.Specification<T> toSpecification(final Object obj) {
		return new JsSpecification<T>(obj);
	}

	public static class JsSpecification<T> implements org.springframework.data.jpa.domain.Specification<T> {

		private static final long serialVersionUID = 1L;
		private static java.util.List<Class<?>> equalFilters;
		private Object obj;

		static {
			equalFilters = new java.util.ArrayList<Class<?>>();
			equalFilters.add(java.lang.Boolean.class);
			equalFilters.add(java.lang.Integer.class);
			equalFilters.add(java.lang.Long.class);
			equalFilters.add(java.lang.Double.class);
			equalFilters.add(java.math.BigInteger.class);
			equalFilters.add(java.math.BigDecimal.class);
		}

		public JsSpecification(Object obj) {
			this.obj = obj;
		}

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

			Predicate predicate = cb.conjunction();

			for (java.lang.reflect.Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				try {
					// Class<?> type = f.getType();
					String fname = f.getName();
					Object val = f.get(obj);
					Class<?> clazz = f.getType();
					if (val == null)
						continue;

					if (equalFilters.contains(clazz) || (clazz instanceof Class && ((Class<?>) clazz).isEnum())) {
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