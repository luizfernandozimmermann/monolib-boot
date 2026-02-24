package monolib.data.domain.permission.converter;

import monolib.core.model.Permission;
import monolib.data.domain.permission.model.PermissionEntity;

import java.util.List;

public interface PermissionConverter {
    List<PermissionEntity> convert(List<Permission> permissions);
    PermissionEntity convert(PermissionEntity entity, Permission permission);
    Permission convert(PermissionEntity entity);
}
