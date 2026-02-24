package monolib.auth.user.service;

import monolib.core.model.User;

public interface UserService {
    User createFirstUser(String email, String username);
    boolean existsAny();
}
