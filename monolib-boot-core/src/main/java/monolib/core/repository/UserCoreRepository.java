package monolib.core.repository;

import monolib.core.model.User;

public interface UserCoreRepository {
    boolean existsAny();

    User save(User user);
}
