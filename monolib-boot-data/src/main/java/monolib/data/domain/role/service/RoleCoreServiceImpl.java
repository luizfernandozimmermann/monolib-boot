package monolib.data.domain.role.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Permission;
import monolib.core.model.Role;
import monolib.core.model.User;
import monolib.core.service.RoleCoreService;
import monolib.data.domain.permission.converter.PermissionConverter;
import monolib.data.domain.role.converter.RoleConverter;
import monolib.data.domain.rolepermission.service.RolePermissionService;
import monolib.data.domain.userrole.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleCoreServiceImpl implements RoleCoreService {

    RoleCRUDService crudService;

    UserRoleService userRoleService;

    RolePermissionService rolePermissionService;

    PermissionConverter permissionConverter;

    @Override
    @Transactional
    public void grantRole(User user, Role role) {
        userRoleService.grantRole(user, role);
    }

    @Override
    @Transactional
    public void create(Role role) {
        crudService.findByName(role.getName()).orElseGet(() -> {
            var entity = RoleConverter.convert(role);
            return crudService.save(entity);
        });
    }

    @Override
    @Transactional
    public void grantPermissionsForAdmin(List<Permission> permissions) {
        rolePermissionService.grantPermissionsForAdmin(permissionConverter.convert(permissions));
    }

    @Override
    @Transactional(readOnly = true)
    public Role getAdminRole() {
        return crudService.findByAdminTrue().map(RoleConverter::convert).orElseThrow();
    }
}
