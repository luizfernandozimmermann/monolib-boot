package br.com.pokedexerp.platform.authentication.repository.impl;

import br.com.pokedexerp.platform.authentication.repository.UserCustomRepository;
import br.com.pokedexerp.platform.user.model.QUserEntity;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private static final QUserEntity qUser = QUserEntity.userEntity;

    JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public boolean existsAny() {
        return queryFactory.select(Expressions.ONE).from(qUser).limit(1L).fetchFirst() != null;
    }
}
