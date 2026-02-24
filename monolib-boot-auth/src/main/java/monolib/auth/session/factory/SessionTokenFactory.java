package monolib.auth.session.factory;

import monolib.core.model.Session;
import monolib.core.model.User;

public interface SessionTokenFactory {
    Session create(User user);
}
