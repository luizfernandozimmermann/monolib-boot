package monolib.data.filter.predicate.builder.operator.impl;

import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.dto.ConditionContext;
import monolib.data.filter.predicate.builder.operator.OperatorPredicateBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class LikeOperatorPredicateBuilder implements OperatorPredicateBuilder {

    @Override
    public Operator getOperator() {
        return Operator.LIKE;
    }

    @Override
    public Predicate build(ConditionContext context) {
        return context.getCriteriaBuilder().like(
                context.getLeft().as(String.class),
                context.getRight().as(String.class)
        );
    }

}
