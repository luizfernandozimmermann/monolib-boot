package monolib.data.domain.user.repository;

import monolib.data.base.repository.EntityBaseRepository;
import monolib.data.domain.user.model.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends EntityBaseRepository<UserEntity>, UserCustomRepository {
}
