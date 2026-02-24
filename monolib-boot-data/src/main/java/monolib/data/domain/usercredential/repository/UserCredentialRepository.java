package monolib.data.domain.usercredential.repository;

import monolib.data.base.repository.EntityBaseRepository;
import monolib.data.domain.usercredential.model.UserCredentialEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends EntityBaseRepository<UserCredentialEntity> {
    Optional<UserCredentialEntity> findByUserEmail(String email);
}
