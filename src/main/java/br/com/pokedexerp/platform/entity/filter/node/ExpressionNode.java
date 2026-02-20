package br.com.pokedexerp.platform.entity.filter.node;

import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Expression;

public interface ExpressionNode extends Node {
    Expression<?> accept(PredicateContext predicateContext);
}
