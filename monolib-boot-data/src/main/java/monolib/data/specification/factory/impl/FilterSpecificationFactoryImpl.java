package monolib.data.specification.factory.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.filter.parser.FilterParser;
import monolib.data.node.QueryNode;
import monolib.data.order.parser.OrderParser;
import monolib.data.specification.FilterSpecification;
import monolib.data.specification.factory.FilterSpecificationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilterSpecificationFactoryImpl implements FilterSpecificationFactory {

    FilterParser filterParser;

    OrderParser orderParser;

    @Override
    public <T> FilterSpecification<T> create(String filter, String orderBy) {
        var queryNode = QueryNode.of(filterParser.parse(filter), orderParser.parse(orderBy));
        return FilterSpecification.<T>of(queryNode);
    }

}
