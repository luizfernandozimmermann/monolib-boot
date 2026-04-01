package monolib.data.domain.permission.repository.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Permission;
import monolib.core.model.User;
import monolib.core.repository.PermissionCoreRepository;
import monolib.data.domain.permission.converter.PermissionConverter;
import monolib.data.domain.permission.model.PermissionEntity;
import monolib.data.domain.permission.repository.PermissionRepository;
import monolib.data.domain.userrole.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionCoreRepositoryImpl implements PermissionCoreRepository {

    PermissionRepository permissionRepository;

    UserRoleRepository userRoleRepository;

    PermissionConverter converter;

    @Override
    @Transactional(readOnly = true)
    public boolean userHasPermission(User user, String[] permissions) {
        return userRoleRepository.userHasPermission(user.getId(), permissions);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Permission> findByResource(String resource) {
        return permissionRepository.findByResource(resource).map(converter::convert);
    }

    @Override
    @Transactional
    public Permission save(Permission permission) {
        var entity = Optional.ofNullable(permission.getResource())
                .flatMap(permissionRepository::findByResource)
                .orElseGet(PermissionEntity::new);
        return converter.convert(permissionRepository.save(converter.convert(entity, permission)));
    }

}
