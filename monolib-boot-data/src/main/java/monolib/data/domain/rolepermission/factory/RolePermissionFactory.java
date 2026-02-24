package monolib.data.domain.rolepermission.factory;

import monolib.data.domain.permission.model.PermissionEntity;
import monolib.data.domain.role.model.RoleEntity;
import monolib.data.domain.rolepermission.model.RolePermissionEntity;

public interface RolePermissionFactory {
    RolePermissionEntity create(RoleEntity role, PermissionEntity permission);
}
