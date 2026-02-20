package br.com.pokedexerp.platform.entity.filter.predicate.dto;

import br.com.pokedexerp.platform.entity.filter.manager.JoinManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import lombok.Getter;

@Getter
public class PredicateContext {

    private final CriteriaBuilder criteriaBuilder;
    private final Root<?> root;
    private final JoinManager joinManager;

    public PredicateContext(CriteriaBuilder criteriaBuilder, Root<?> root) {
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
        this.joinManager = new JoinManager(root);
    }

    public static PredicateContext of(CriteriaBuilder criteriaBuilder, Root<?> root) {
        return new PredicateContext(criteriaBuilder, root);
    }
}
