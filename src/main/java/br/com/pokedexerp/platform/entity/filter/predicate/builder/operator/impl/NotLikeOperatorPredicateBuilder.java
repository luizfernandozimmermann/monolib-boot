package br.com.pokedexerp.platform.entity.filter.predicate.builder.operator.impl;

import br.com.pokedexerp.platform.entity.filter.operator.Operator;
import br.com.pokedexerp.platform.entity.filter.predicate.builder.operator.OperatorPredicateBuilder;
import br.com.pokedexerp.platform.entity.filter.predicate.dto.ConditionContext;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotLikeOperatorPredicateBuilder implements OperatorPredicateBuilder {

    LikeOperatorPredicateBuilder likeOperatorPredicateBuilder;

    @Override
    public Operator getOperator() {
        return Operator.NOT_LIKE;
    }

    @Override
    public Predicate build(ConditionContext context) {
        return likeOperatorPredicateBuilder.build(context).not();
    }

}
