package br.com.pokedexerp.platform.entity.filter.node;

import br.com.pokedexerp.platform.entity.filter.predicate.builder.ExpressionBuilder;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Expression;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class FieldNode implements ExpressionNode {

    private final String field;

    @Override
    public Expression<?> accept(PredicateContext predicateContext) {
        return ExpressionBuilder.build(predicateContext, this);
    }

}
