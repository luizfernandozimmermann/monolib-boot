package monolib.data.filter.predicate.builder;

import monolib.data.filter.node.FunctionNode;
import monolib.data.filter.predicate.dto.PredicateContext;
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
