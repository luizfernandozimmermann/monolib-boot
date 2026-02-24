package monolib.data.order.builder;

import jakarta.persistence.criteria.Order;
import lombok.experimental.UtilityClass;
import monolib.data.filter.predicate.builder.ExpressionBuilder;
import monolib.data.filter.predicate.dto.PredicateContext;
import monolib.data.order.node.OrderNode;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class OrderBuilder {

    public static List<Order> build(PredicateContext predicateContext, List<OrderNode> orderNodes) {
        if (orderNodes.isEmpty()) {
            return Collections.emptyList();
        }
        return orderNodes.stream()
                .map(orderNode -> buildOrder(predicateContext, orderNode))
                .toList();
    }

    private static Order buildOrder(PredicateContext predicateContext, OrderNode orderNode) {
        var expression = ExpressionBuilder.build(predicateContext, orderNode.getExpression());
        if (orderNode.isAscending()) {
            return predicateContext.getCriteriaBuilder().asc(expression);
        }
        return predicateContext.getCriteriaBuilder().desc(expression);
    }

}
