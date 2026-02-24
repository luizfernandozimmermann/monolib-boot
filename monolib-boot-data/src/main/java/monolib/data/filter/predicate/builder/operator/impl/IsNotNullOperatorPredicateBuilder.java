package monolib.data.filter.predicate.builder.operator.impl;

import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.dto.ConditionContext;
import monolib.data.filter.predicate.builder.operator.OperatorPredicateBuilder;
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
