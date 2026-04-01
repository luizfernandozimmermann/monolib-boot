package monolib.data.domain.rolepermission.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.domain.permission.model.PermissionEntity;
import monolib.data.domain.role.model.RoleEntity;
import monolib.data.domain.role.repository.RoleRepository;
import monolib.data.domain.rolepermission.factory.RolePermissionFactory;
import monolib.data.domain.rolepermission.repository.RolePermissionRepository;
import monolib.data.domain.rolepermission.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolePermissionServiceImpl implements RolePermissionService {

    RoleRepository roleRepository;

    RolePermissionRepository repository;

    RolePermissionFactory factory;

    @Override
    @Transactional
    public void grantPermissionsForAdmin(List<PermissionEntity> permissions) {
        roleRepository.findByAdminTrue().ifPresent(adminRole ->
                permissions.stream()
                        .filter(permission -> !repository.existsByRoleAndPermission(adminRole, permission))
                        .forEach(permission -> grantPermission(adminRole, permission))
        );

    }

    private void grantPermission(RoleEntity role, PermissionEntity permission) {
        var rolePermission = factory.create(role, permission);
        repository.save(rolePermission);
    }

}
