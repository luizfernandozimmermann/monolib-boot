package monolib.auth.session.service;

import monolib.core.model.Session;
import monolib.core.model.User;

public interface SessionService {
    Session findByAccessToken(String accessToken);

    Session findByRefreshToken(String refreshToken);

    void revokeSession(Session session);

    Session createSession(User user, boolean firstSession);

    Session createSession(User user);

    void revokeAllUserSessions(User user);
}
