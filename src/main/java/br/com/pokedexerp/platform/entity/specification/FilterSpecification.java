package br.com.pokedexerp.platform.entity.specification;

import br.com.pokedexerp.platform.entity.filter.predicate.builder.PredicateBuilder;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.PredicateContext;
import br.com.pokedexerp.platform.entity.node.QueryNode;
import br.com.pokedexerp.platform.entity.order.builder.OrderBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@AllArgsConstructor(staticName = "of")
public class FilterSpecification<T> implements Specification<T> {

    private final transient QueryNode queryNode;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        if (Objects.isNull(queryNode)) {
            return cb.conjunction();
        }
        var predicateContext = PredicateContext.of(cb, root);
        query.orderBy(OrderBuilder.build(predicateContext, queryNode.getOrderNode()));
        return PredicateBuilder.build(predicateContext, queryNode.getFilterNode());
    }

}
