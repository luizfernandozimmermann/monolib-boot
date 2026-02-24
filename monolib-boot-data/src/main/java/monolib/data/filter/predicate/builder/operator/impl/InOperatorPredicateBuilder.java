package monolib.data.filter.predicate.builder.operator.impl;

import monolib.data.filter.converter.TypeConverter;
import monolib.data.filter.node.ListValueNode;
import monolib.data.filter.node.ValueNode;
import monolib.data.filter.operator.Operator;
import monolib.data.filter.predicate.dto.ConditionContext;
import monolib.data.filter.predicate.builder.operator.OperatorPredicateBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class InOperatorPredicateBuilder implements OperatorPredicateBuilder {

    @Override
    public Operator getOperator() {
        return Operator.IN;
    }

    @Override
    public boolean isComparison() {
        return false;
    }

    @Override
    public Predicate build(ConditionContext context) {
        var list = (ListValueNode) context.getNode().getRight();
        var in = context.getCriteriaBuilder().<Object>in(context.getLeft());
        list.getValues().forEach(v -> {
            var raw = ((ValueNode) v).getValue();
            var converted = TypeConverter.convert(raw, context.getLeft().getJavaType());
            in.value(converted);
        });
        return in;
    }

}
