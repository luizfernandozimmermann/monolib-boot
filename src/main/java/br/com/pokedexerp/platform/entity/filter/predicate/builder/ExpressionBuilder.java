package br.com.pokedexerp.platform.entity.filter.predicate.builder;

import br.com.pokedexerp.platform.entity.filter.converter.TypeConverter;
import br.com.pokedexerp.platform.entity.filter.node.ExpressionNode;
import br.com.pokedexerp.platform.entity.filter.node.FieldNode;
import br.com.pokedexerp.platform.entity.filter.node.FunctionNode;
import br.com.pokedexerp.platform.entity.filter.node.ValueNode;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import br.com.pokedexerp.platform.messaging.context.ServiceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Expression;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;

@UtilityClass
public class ExpressionBuilder {

    public static Expression<?> build(PredicateContext predicateContext, ExpressionNode node) {
        return node.accept(predicateContext);
    }

    public static Expression<?> build(PredicateContext predicateContext, FieldNode node) {
        if (node.getField().equals("active")) {
            disableActiveFilter();
        }
        if (node.getField().contains(".")) {
            return predicateContext.getJoinManager().resolve(node.getField());
        }
        return predicateContext.getRoot().get(node.getField());
    }

    private static void disableActiveFilter() {
        ServiceContext.getApplicationContext().getBean(EntityManager.class)
                .unwrap(Session.class)
                .disableFilter("activeFilter");
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
