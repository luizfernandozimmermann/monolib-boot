package monolib.data.domain.userrole.repository;

import monolib.data.domain.userrole.model.UserRoleBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends UserRoleBaseRepository, UserRoleCustomRepository {
}
