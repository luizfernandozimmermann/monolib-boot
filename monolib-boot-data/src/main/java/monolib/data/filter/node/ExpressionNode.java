package monolib.data.filter.node;

import monolib.data.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Expression;

public interface ExpressionNode extends Node {
    Expression<?> accept(PredicateContext predicateContext);
}
