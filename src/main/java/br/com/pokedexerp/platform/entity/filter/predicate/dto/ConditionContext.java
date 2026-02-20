package br.com.pokedexerp.platform.entity.filter.predicate.dto;

import br.com.pokedexerp.platform.entity.filter.node.ConditionNode;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ConditionContext {

    private final ConditionNode node;
    private final Expression<?> left;
    private final CriteriaBuilder criteriaBuilder;
    @Setter
    private Expression<?> right;

    public static ConditionContext of(ConditionNode node, Expression<?> left, CriteriaBuilder criteriaBuilder) {
        return new ConditionContext(node, left, criteriaBuilder);
    }

    public ConditionContext(ConditionNode node, Expression<?> left, CriteriaBuilder criteriaBuilder) {
        this.node = node;
        this.left = left;
        this.criteriaBuilder = criteriaBuilder;
    }

}
