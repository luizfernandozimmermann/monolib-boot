package br.com.pokedexerp.platform.entity.filter.node;

import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Predicate;

public interface PredicateNode extends Node {

    Predicate accept(PredicateContext predicateContext);

}
