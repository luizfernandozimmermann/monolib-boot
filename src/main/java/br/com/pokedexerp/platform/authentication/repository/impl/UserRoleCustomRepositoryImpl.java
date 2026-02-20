package br.com.pokedexerp.platform.authentication.repository.impl;

import br.com.pokedexerp.platform.authentication.model.QPermissionEntity;
import br.com.pokedexerp.platform.authentication.model.QRoleEntity;
import br.com.pokedexerp.platform.authentication.model.QRolePermissionEntity;
import br.com.pokedexerp.platform.authentication.model.QUserRoleEntity;
import br.com.pokedexerp.platform.authentication.repository.UserRoleCustomRepository;
import br.com.pokedexerp.platform.user.model.QUserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleCustomRepositoryImpl implements UserRoleCustomRepository {

    private static final QUserEntity qUser = QUserEntity.userEntity;
    private static final QUserRoleEntity qUserRole = QUserRoleEntity.userRoleEntity;
    private static final QRoleEntity qRole = QRoleEntity.roleEntity;
    private static final QRolePermissionEntity qRolePermission = QRolePermissionEntity.rolePermissionEntity;
    private static final QPermissionEntity qPermission = QPermissionEntity.permissionEntity;

    JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public boolean userHasPermission(UUID userId, String[] permission) {
        var predicate = qUser.id.eq(userId)
                .and(qPermission.resource.in(permission).or(qRole.admin).or(qUser.admin));

        var query = queryFactory.select(qPermission.count()).from(qUserRole)
                .innerJoin(qUser).on(qUserRole.user.eq(qUser))
                .innerJoin(qRole).on(qUserRole.role.eq(qRole))
                .innerJoin(qRolePermission).on(qRolePermission.role.eq(qRole))
                .innerJoin(qPermission).on(qRolePermission.permission.eq(qPermission))
                .where(predicate);

        var count = query.fetchFirst();
        return count > 0;
    }

}
