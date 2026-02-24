package monolib.data.domain.rolepermission.factory.impl;

import monolib.data.domain.permission.model.PermissionEntity;
import monolib.data.domain.role.model.RoleEntity;
import monolib.data.domain.rolepermission.factory.RolePermissionFactory;
import monolib.data.domain.rolepermission.model.RolePermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionFactoryImpl implements RolePermissionFactory {

    @Override
    public RolePermissionEntity create(RoleEntity role, PermissionEntity permission) {
        var rolePermission = new RolePermissionEntity();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        return rolePermission;
    }

}
