package monolib.data.specification.factory;

import monolib.data.specification.FilterSpecification;

public interface FilterSpecificationFactory {

    <T> FilterSpecification<T> create(String filter, String orderBy);

}
