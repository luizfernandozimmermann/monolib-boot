package monolib.data.domain.userrole.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.domain.permission.model.QPermissionEntity;
import monolib.data.domain.role.model.QRoleEntity;
import monolib.data.domain.rolepermission.model.QRolePermissionEntity;
import monolib.data.domain.user.model.QUserEntity;
import monolib.data.domain.userrole.model.QUserRoleEntity;
import monolib.data.domain.userrole.repository.UserRoleCustomRepository;
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
