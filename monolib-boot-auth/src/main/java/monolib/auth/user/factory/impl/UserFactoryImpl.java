package monolib.auth.user.factory.impl;

import monolib.auth.user.factory.UserFactory;
import monolib.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserFactoryImpl implements UserFactory {

    @Override
    public User create(String email, String username) {
        var user = new User();
        user.setEmail(email);
        user.setName(username);
        return user;
    }

}
