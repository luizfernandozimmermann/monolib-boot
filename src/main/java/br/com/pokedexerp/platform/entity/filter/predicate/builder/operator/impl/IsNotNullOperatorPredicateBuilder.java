package br.com.pokedexerp.platform.entity.filter.predicate.builder.operator.impl;

import br.com.pokedexerp.platform.entity.filter.operator.Operator;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.ConditionContext;
import br.com.pokedexerp.platform.entity.filter.predicate.builder.operator.OperatorPredicateBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class IsNotNullOperatorPredicateBuilder implements OperatorPredicateBuilder {

    @Override
    public Operator getOperator() {
        return Operator.IS_NOT_NULL;
    }

    @Override
    public boolean isComparison() {
        return false;
    }

    @Override
    public Predicate build(ConditionContext context) {
        return context.getCriteriaBuilder().isNotNull(context.getLeft());
    }

}
