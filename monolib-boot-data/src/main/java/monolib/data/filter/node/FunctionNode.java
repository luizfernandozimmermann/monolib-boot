package monolib.data.filter.node;

import monolib.data.filter.predicate.builder.ExpressionBuilder;
import monolib.data.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Expression;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public final class FunctionNode implements ExpressionNode {

    private final String functionName;
    private final List<ExpressionNode> arguments;

    @Override
    public Expression<?> accept(PredicateContext predicateContext) {
        return ExpressionBuilder.build(predicateContext, this);
    }

}
