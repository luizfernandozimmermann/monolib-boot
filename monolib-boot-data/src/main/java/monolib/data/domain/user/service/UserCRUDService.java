package monolib.data.domain.user.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.base.service.EntityCRUDServiceBase;
import monolib.data.domain.user.model.UserEntity;
import monolib.data.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCRUDService extends EntityCRUDServiceBase<UserEntity> {

    UserRepository repository;

    @Transactional(readOnly = true)
    public boolean existsAny() {
        return repository.existsAny();
    }

}
