package monolib.data.filter.predicate.builder.operator;

import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.dto.ConditionContext;
import jakarta.persistence.criteria.Predicate;

public interface OperatorPredicateBuilder {

    Operator getOperator();

    default boolean isComparison() {
        return true;
    }

    Predicate build(ConditionContext context);
}
