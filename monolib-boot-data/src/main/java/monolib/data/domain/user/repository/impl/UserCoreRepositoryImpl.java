package monolib.data.domain.user.repository.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.User;
import monolib.core.repository.UserCoreRepository;
import monolib.data.domain.user.converter.UserConverter;
import monolib.data.domain.user.model.UserEntity;
import monolib.data.domain.user.service.UserCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCoreRepositoryImpl implements UserCoreRepository {

    UserCRUDService crudService;

    @Override
    @Transactional(readOnly = true)
    public boolean existsAny() {
        return crudService.existsAny();
    }

    @Override
    @Transactional
    public User save(User user) {
        var entity = Optional.ofNullable(user.getId())
                .flatMap(crudService::findById)
                .orElseGet(UserEntity::new);
        return UserConverter.convert(crudService.save(UserConverter.convert(entity, user)));
    }
}
