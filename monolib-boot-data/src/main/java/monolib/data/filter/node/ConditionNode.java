package monolib.data.filter.node;

import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.builder.PredicateBuilder;
import monolib.data.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class ConditionNode implements PredicateNode {

    private final ExpressionNode left;
    private final Operator operator;
    private final ExpressionNode right;

    @Override
    public Predicate accept(PredicateContext predicateContext) {
        return PredicateBuilder.build(predicateContext, this);
    }

}
