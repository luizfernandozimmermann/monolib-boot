package monolib.data.domain.role.repository;

import monolib.data.domain.role.model.RoleBaseRepository;
import monolib.data.domain.role.model.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends RoleBaseRepository {
    Optional<RoleEntity> findByName(String name);
    Optional<RoleEntity> findByAdminTrue();
}
