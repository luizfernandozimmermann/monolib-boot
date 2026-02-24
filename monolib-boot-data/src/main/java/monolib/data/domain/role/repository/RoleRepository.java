package monolib.data.domain.role.repository;

import monolib.data.base.repository.EntityBaseRepository;
import monolib.data.domain.role.model.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends EntityBaseRepository<RoleEntity> {
    Optional<RoleEntity> findByName(String name);
    Optional<RoleEntity> findByAdminTrue();
}
