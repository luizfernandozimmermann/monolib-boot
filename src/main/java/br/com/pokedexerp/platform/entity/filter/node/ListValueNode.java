package br.com.pokedexerp.platform.entity.filter.node;

import br.com.pokedexerp.platform.entity.filter.predicate.builder.ExpressionBuilder;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Expression;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListValueNode implements ExpressionNode {

    private final List<ExpressionNode> values;

    @Override
    public Expression<?> accept(PredicateContext predicateContext) {
        return ExpressionBuilder.build(predicateContext, this);
    }

}
