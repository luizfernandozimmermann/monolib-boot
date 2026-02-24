package monolib.data.domain.userrole.repository;

import monolib.data.base.repository.EntityBaseRepository;
import monolib.data.domain.userrole.model.UserRoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends EntityBaseRepository<UserRoleEntity>, UserRoleCustomRepository {
}
