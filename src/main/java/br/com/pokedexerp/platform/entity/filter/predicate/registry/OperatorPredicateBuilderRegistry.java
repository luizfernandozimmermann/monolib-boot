package br.com.pokedexerp.platform.entity.filter.predicate.registry;

import br.com.pokedexerp.platform.entity.filter.operator.Operator;
import br.com.pokedexerp.platform.entity.filter.predicate.builder.operator.OperatorPredicateBuilder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class OperatorPredicateBuilderRegistry {

    private static final Map<Operator, OperatorPredicateBuilder> MAP = new EnumMap<>(Operator.class);

    @Inject
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
