package sangmok.util.jsgrid;

import java.lang.reflect.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.demo.data.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

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
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

            Predicate predicate = cb.conjunction();

            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    Class<?> type = f.getType();
                    String fname = f.getName();
                    Object val = f.get(obj);
                    // logger.info("{}={}", fname, val);
                    if (val == null)
                        continue;

                    if (val.toString().isEmpty())
                        continue;

                    Predicate p = cb.like(root.get(fname), String.format("%%%s%%", val));
                    predicate = cb.and(predicate, p);

                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                logger.info(predicate.toString());
            }
            return predicate;
        }

    }

    // public static void main(String[] arts) {
    // Member e1 = new Member();
    // e1.setUsername("admin");
    // e1.setEnabled(true);
    // Specification<Object> specs = JsGridSpecificationFactory.toSpecification(e1);
    // specs.toPredicate(null, null, null);
    // }

}