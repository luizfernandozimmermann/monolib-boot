package monolib.data.domain.permission.repository;

import monolib.data.domain.permission.model.PermissionBaseRepository;
import monolib.data.domain.permission.model.PermissionEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends PermissionBaseRepository {
    Optional<PermissionEntity> findByResource(String resource);
}
