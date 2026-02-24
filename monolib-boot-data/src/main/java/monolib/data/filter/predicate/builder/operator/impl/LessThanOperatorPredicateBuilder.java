package monolib.data.filter.predicate.builder.operator.impl;

import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.dto.ConditionContext;
import monolib.data.filter.predicate.builder.operator.OperatorPredicateBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class LessThanOperatorPredicateBuilder implements OperatorPredicateBuilder {

    @Override
    public Operator getOperator() {
        return Operator.LESS_THAN;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Predicate build(ConditionContext context) {
        return context.getCriteriaBuilder().lessThan(
                context.getLeft().as(Comparable.class),
                ((Expression<Comparable>) context.getRight())
        );
    }

}
