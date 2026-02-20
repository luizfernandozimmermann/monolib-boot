package br.com.pokedexerp.platform.entity.order.builder;

import br.com.pokedexerp.platform.entity.filter.predicate.builder.ExpressionBuilder;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import br.com.pokedexerp.platform.entity.order.node.OrderNode;
import jakarta.persistence.criteria.Order;
import lombok.experimental.UtilityClass;

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
