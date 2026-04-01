package monolib.data.domain.permission.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Permission;
import monolib.data.domain.permission.model.PermissionEntity;
import monolib.data.domain.permission.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionConverterImpl implements PermissionConverter {

    PermissionRepository permissionRepository;

    @Override
    public List<PermissionEntity> convert(List<Permission> permissions) {
        return permissions.stream().map(permission -> convert(new PermissionEntity(), permission)).toList();
    }

    @Override
    public PermissionEntity convert(PermissionEntity entity, Permission permission) {
        entity.setId(permission.getId());
        entity.setResource(permission.getResource());
        entity.setDescription(permission.getDescription());
        Optional.ofNullable(permission.getParent())
                .map(Permission::getId)
                .flatMap(permissionRepository::findById)
                .ifPresent(entity::setParentPermission);
        return entity;
    }

    @Override
    public Permission convert(PermissionEntity entity) {
        var permission = new Permission();
        permission.setId(entity.getId());
        permission.setResource(entity.getResource());
        permission.setDescription(entity.getDescription());
        return permission;
    }

}
