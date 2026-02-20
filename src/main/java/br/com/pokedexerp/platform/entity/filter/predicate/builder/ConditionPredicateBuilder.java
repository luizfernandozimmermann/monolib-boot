package br.com.pokedexerp.platform.entity.filter.predicate.builder;

import br.com.pokedexerp.platform.entity.filter.node.ConditionNode;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.ConditionContext;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import br.com.pokedexerp.platform.entity.filter.predicate.registry.OperatorPredicateBuilderRegistry;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConditionPredicateBuilder {

    public static Predicate build(PredicateContext predicateContext, ConditionNode node) {
        var left = ExpressionBuilder.build(predicateContext, node.getLeft());
        var context = ConditionContext.of(node, left, predicateContext.getCriteriaBuilder());

        var operatorBuilder = OperatorPredicateBuilderRegistry.get(node.getOperator());
        if (operatorBuilder.isComparison()) {
            context.setRight(ExpressionBuilder.buildRight(predicateContext, node.getRight(), context.getLeft()));
        }
        return operatorBuilder.build(context);
    }

}
