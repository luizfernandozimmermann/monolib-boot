package monolib.data.filter.predicate.builder;

import jakarta.persistence.criteria.Expression;
import lombok.experimental.UtilityClass;
import monolib.data.filter.converter.TypeConverter;
import monolib.data.filter.node.ExpressionNode;
import monolib.data.filter.node.FieldNode;
import monolib.data.filter.node.FunctionNode;
import monolib.data.filter.node.ValueNode;
import monolib.data.filter.predicate.dto.PredicateContext;
import monolib.data.utils.ActiveFilterUtils;

@UtilityClass
public class ExpressionBuilder {

    public static Expression<?> build(PredicateContext predicateContext, ExpressionNode node) {
        return node.accept(predicateContext);
    }

    public static Expression<?> build(PredicateContext predicateContext, FieldNode node) {
        if (node.getField().equals("active")) {
            ActiveFilterUtils.disable();
        }
        if (node.getField().contains(".")) {
            return predicateContext.getJoinManager().resolve(node.getField());
        }
        return predicateContext.getRoot().get(node.getField());
    }

    public static Expression<?> build(PredicateContext predicateContext, ValueNode node) {
        return predicateContext.getCriteriaBuilder().literal(node.getValue());
    }

    public static Expression<?> build(PredicateContext predicateContext, FunctionNode node) {
        return FunctionPredicateBuilder.build(predicateContext, node);
    }

    public static Expression<?> buildRight(PredicateContext predicateContext, ExpressionNode node, Expression<?> left) {
        if (node instanceof ValueNode value) {
            var converted = TypeConverter.convert(value.getValue(), left.getJavaType());
            return predicateContext.getCriteriaBuilder().literal(converted);
        }
        return build(predicateContext, node);
    }

}
