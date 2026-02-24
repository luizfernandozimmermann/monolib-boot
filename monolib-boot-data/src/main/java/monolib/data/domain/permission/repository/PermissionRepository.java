package monolib.data.domain.permission.repository;

import monolib.data.base.repository.EntityBaseRepository;
import monolib.data.domain.permission.model.PermissionEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends EntityBaseRepository<PermissionEntity> {
    Optional<PermissionEntity> findByResource(String resource);
}
