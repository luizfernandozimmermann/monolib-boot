package br.com.pokedexerp.platform.entity.filter.predicate.builder;

import br.com.pokedexerp.platform.entity.filter.node.FunctionNode;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Expression;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FunctionPredicateBuilder {

    public static Expression<?> build(PredicateContext predicateContext, FunctionNode node) {
        var args = node.getArguments()
                .stream()
                .map(argNode -> ExpressionBuilder.build(predicateContext, argNode))
                .toList();

        return predicateContext.getCriteriaBuilder().function(
                node.getFunctionName(),
                Object.class,
                args.toArray(new Expression[0])
        );
    }

}
