package monolib.auth.user.factory;

import monolib.core.model.User;

public interface UserFactory {
    User create(String email, String username);
}
