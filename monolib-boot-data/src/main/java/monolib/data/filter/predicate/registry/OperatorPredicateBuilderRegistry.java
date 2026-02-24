package monolib.data.filter.predicate.registry;

import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.builder.operator.OperatorPredicateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class OperatorPredicateBuilderRegistry {

    private static final Map<Operator, OperatorPredicateBuilder> MAP = new EnumMap<>(Operator.class);

    @Autowired
    private OperatorPredicateBuilderRegistry(List<OperatorPredicateBuilder> filters) {
        filters.forEach(filter -> MAP.put(filter.getOperator(), filter));
    }

    public static OperatorPredicateBuilder get(Operator operator) {
        var filter = MAP.get(operator);
        if (filter == null) {
            throw new IllegalArgumentException("Operator %s does not have a filter".formatted(operator.name()));
        }
        return filter;
    }

}
