package monolib.auth.user.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.auth.user.factory.UserFactory;
import monolib.auth.user.service.UserService;
import monolib.core.model.User;
import monolib.core.repository.UserCoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserCoreRepository repository;

    UserFactory factory;

    @Override
    @Transactional(readOnly = true)
    public boolean existsAny() {
        return repository.existsAny();
    }

    @Override
    @Transactional
    public User createFirstUser(String email, String username) {
        var user = factory.create(email, username);
        user.setAdmin(true);
        return repository.save(user);
    }

}
