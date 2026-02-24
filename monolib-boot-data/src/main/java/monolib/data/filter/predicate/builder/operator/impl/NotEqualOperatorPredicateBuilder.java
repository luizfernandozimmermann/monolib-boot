package monolib.data.filter.predicate.builder.operator.impl;

import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.dto.ConditionContext;
import monolib.data.filter.predicate.builder.operator.OperatorPredicateBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class NotEqualOperatorPredicateBuilder implements OperatorPredicateBuilder {

    @Override
    public Operator getOperator() {
        return Operator.NOT_EQUAL;
    }

    @Override
    public Predicate build(ConditionContext context) {
        return context.getCriteriaBuilder().notEqual(context.getLeft(), context.getRight());
    }

}
