package monolib.auth.permission.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Permission;
import monolib.core.repository.PermissionCoreRepository;
import monolib.core.service.PermissionCoreService;
import monolib.core.service.RoleCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionCoreServiceImpl implements PermissionCoreService {

    PermissionCoreRepository repository;

    RoleCoreService roleService;

    @Override
    @Transactional
    public void create(List<Permission> permissions) {
        var allPermissions = new ArrayList<Permission>();
        this.create(permissions, allPermissions, null);
        roleService.grantPermissionsForAdmin(allPermissions);
    }

    private void create(List<Permission> permissions, List<Permission> allPermissions, Permission parent) {
        permissions.forEach(permissionSeed -> create(permissionSeed, allPermissions, parent));
    }

    private void create(Permission permission, List<Permission> allPermissions, Permission parentPermission) {
        repository.findByResource(permission.getResource()).ifPresentOrElse(allPermissions::add, () -> {
            permission.setParent(parentPermission);
            var permissionSaved = repository.save(permission);
            allPermissions.add(permissionSaved);
            create(permission.getChildren(), allPermissions, permissionSaved);
        });
    }

}
