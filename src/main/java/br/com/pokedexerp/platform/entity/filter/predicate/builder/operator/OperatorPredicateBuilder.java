package br.com.pokedexerp.platform.entity.filter.predicate.builder.operator;

import br.com.pokedexerp.platform.entity.filter.operator.Operator;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.ConditionContext;
import jakarta.persistence.criteria.Predicate;

public interface OperatorPredicateBuilder {

    Operator getOperator();

    default boolean isComparison() {
        return true;
    }

    Predicate build(ConditionContext context);
}
