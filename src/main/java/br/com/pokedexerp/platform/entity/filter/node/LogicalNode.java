package br.com.pokedexerp.platform.entity.filter.node;

import br.com.pokedexerp.platform.entity.filter.operator.LogicalOperator;
import br.com.pokedexerp.platform.entity.filter.predicate.builder.PredicateBuilder;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class LogicalNode implements PredicateNode {

    private Node left;
    private Node right;
    private LogicalOperator operator;

    @Override
    public Predicate accept(PredicateContext predicateContext) {
        return PredicateBuilder.build(predicateContext, this);
    }

}
