package monolib.data.filter.predicate.builder;

import monolib.data.filter.node.ConditionNode;
import monolib.data.filter.predicate.dto.ConditionContext;
import monolib.data.filter.predicate.dto.PredicateContext;
import monolib.data.filter.predicate.registry.OperatorPredicateBuilderRegistry;
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
