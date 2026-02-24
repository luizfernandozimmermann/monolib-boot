package monolib.data.domain.rolepermission.service;

import monolib.data.domain.permission.model.PermissionEntity;

import java.util.List;

public interface RolePermissionService {
    void grantPermissionsForAdmin(List<PermissionEntity> allPermissions);
}
