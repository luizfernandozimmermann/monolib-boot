package br.com.pokedexerp.platform.entity.filter.node;

import br.com.pokedexerp.platform.entity.filter.operator.Operator;
import br.com.pokedexerp.platform.entity.filter.predicate.builder.PredicateBuilder;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class ConditionNode implements PredicateNode {

    private final ExpressionNode left;
    private final Operator operator;
    private final ExpressionNode right;

    @Override
    public Predicate accept(PredicateContext predicateContext) {
        return PredicateBuilder.build(predicateContext, this);
    }

}
