package br.com.pokedexerp.platform.entity.filter.predicate.builder.operator.impl;

import br.com.pokedexerp.platform.entity.filter.converter.TypeConverter;
import br.com.pokedexerp.platform.entity.filter.node.ListValueNode;
import br.com.pokedexerp.platform.entity.filter.node.ValueNode;
import br.com.pokedexerp.platform.entity.filter.operator.Operator;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.ConditionContext;
import br.com.pokedexerp.platform.entity.filter.predicate.builder.operator.OperatorPredicateBuilder;
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
