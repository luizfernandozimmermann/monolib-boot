package monolib.core.repository;

import monolib.core.model.Session;
import monolib.core.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SessionCoreRepository {
    Optional<Session> findByAccessTokenAndNotRevoked(String accessToken);

    Optional<Session> findByRefreshToken(String refreshToken);

    void revokeSession(Session session, LocalDateTime revokedAt);

    void revokeAllSessionsByUser(User user);

    Session save(Session session);

    Optional<Session> findLastSessionOfUser(User user);
}
