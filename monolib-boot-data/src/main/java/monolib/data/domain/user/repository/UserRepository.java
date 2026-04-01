package monolib.data.domain.user.repository;

import monolib.data.domain.user.model.UserBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends UserBaseRepository, UserCustomRepository {
}
