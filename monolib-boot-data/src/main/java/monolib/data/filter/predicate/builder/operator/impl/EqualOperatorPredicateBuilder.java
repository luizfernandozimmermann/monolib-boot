package monolib.data.filter.predicate.builder.operator.impl;

import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.dto.ConditionContext;
import monolib.data.filter.predicate.builder.operator.OperatorPredicateBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class EqualOperatorPredicateBuilder implements OperatorPredicateBuilder {

    @Override
    public Operator getOperator() {
        return Operator.EQUAL;
    }

    @Override
    public Predicate build(ConditionContext context) {
        return context.getCriteriaBuilder().equal(context.getLeft(), context.getRight());
    }

}
