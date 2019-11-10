package sangmok.util.jsgrid;

import java.lang.reflect.Field;

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
//                    Class<?> type = f.getType();
					String fname = f.getName();
					Object val = f.get(obj);
					if (val == null)
						continue;
// TODO 유형별로 디버깅으로 해야 한다. 우선 Integer 는 오류
					if (obj instanceof Boolean) {
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