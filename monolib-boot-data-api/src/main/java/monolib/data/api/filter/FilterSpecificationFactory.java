package monolib.data.api.filter;

import org.springframework.data.jpa.domain.Specification;

public interface FilterSpecificationFactory {

    <T> Specification<T> create(String filter, String orderBy);

}
