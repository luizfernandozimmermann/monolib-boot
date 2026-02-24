package monolib.data.filter.predicate.builder;

import monolib.data.filter.node.LogicalNode;
import monolib.data.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogicalPredicateBuilder {

    public static Predicate build(PredicateContext predicateContext, LogicalNode node) {
        var left = PredicateBuilder.build(predicateContext, node.getLeft());
        var right = PredicateBuilder.build(predicateContext, node.getRight());
        return switch (node.getOperator()) {
            case AND -> predicateContext.getCriteriaBuilder().and(left, right);
            case OR -> predicateContext.getCriteriaBuilder().or(left, right);
        };
    }

}
