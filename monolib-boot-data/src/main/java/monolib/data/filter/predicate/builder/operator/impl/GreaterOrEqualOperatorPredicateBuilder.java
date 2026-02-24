package monolib.data.filter.predicate.builder.operator.impl;

import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.dto.ConditionContext;
import monolib.data.filter.predicate.builder.operator.OperatorPredicateBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class GreaterOrEqualOperatorPredicateBuilder implements OperatorPredicateBuilder {

    @Override
    public Operator getOperator() {
        return Operator.GREATER_OR_EQUAL;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Predicate build(ConditionContext context) {
        return context.getCriteriaBuilder().greaterThanOrEqualTo(
                context.getLeft().as(Comparable.class),
                ((Expression<Comparable>) context.getRight())
        );
    }

}
