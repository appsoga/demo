package sangmok.util.jsgrid;

import java.lang.reflect.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sangmok.util.datatables.Utils;

public class JsGridSpecificationFactory {

    private static Logger logger = LoggerFactory.getLogger(JsGridSpecificationFactory.class);

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
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    Object val = f.get(obj);
                    logger.info("{}={}", f.getName(), val);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // TODO Auto-generated method stub

            return null;
        }

        private static <S> Expression<S> getExpression(Root<?> root, String columnData, Class<S> clazz) {
            if (!columnData.contains(Utils.ATTRIBUTE_SEPARATOR)) {
                // columnData is like "attribute" so nothing particular to do
                return root.get(columnData).as(clazz);
            }
            // columnData is like "joinedEntity.attribute" so add a join clause
            String[] values = columnData.split(Utils.ESCAPED_ATTRIBUTE_SEPARATOR);
            if (root.getModel().getAttribute(values[0])
                    .getPersistentAttributeType() == PersistentAttributeType.EMBEDDED) {
                // with @Embedded attribute
                return root.get(values[0]).get(values[1]).as(clazz);
            }
            From<?, ?> from = root;
            for (int i = 0; i < values.length - 1; i++) {
                from = from.join(values[i], JoinType.LEFT);
            }
            return from.get(values[values.length - 1]).as(clazz);
        }

    }

    // public static void main(String[] arts) {
    // Member e1 = new Member();
    // e1.setUsername("admin");
    // Specification<Object> specs = JsGridSpecificationFactory.toSpecification(e1);
    // specs.toPredicate(null, null, null);
    // }

}