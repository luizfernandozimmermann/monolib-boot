package monolib.data.domain.rolepermission.repository;

import monolib.data.domain.permission.model.PermissionEntity;
import monolib.data.domain.role.model.RoleEntity;
import monolib.data.domain.rolepermission.model.RolePermissionBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends RolePermissionBaseRepository {
    boolean existsByRoleAndPermission(RoleEntity role, PermissionEntity permission);
}
