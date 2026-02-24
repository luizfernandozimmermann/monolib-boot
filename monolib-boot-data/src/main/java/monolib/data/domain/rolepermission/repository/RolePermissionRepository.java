package monolib.data.domain.rolepermission.repository;

import monolib.data.base.repository.EntityBaseRepository;
import monolib.data.domain.permission.model.PermissionEntity;
import monolib.data.domain.role.model.RoleEntity;
import monolib.data.domain.rolepermission.model.RolePermissionEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends EntityBaseRepository<RolePermissionEntity> {
    boolean existsByRoleAndPermission(RoleEntity role, PermissionEntity permission);
}
