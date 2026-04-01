package monolib.data.domain.usersession.repository;

import monolib.data.domain.usersession.model.UserSessionBaseRepository;
import monolib.data.domain.usersession.model.UserSessionEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSessionRepository extends UserSessionBaseRepository, UserSessionCustomRepository {
    Optional<UserSessionEntity> findByAccessTokenHashAndRevokedAtNull(String accessTokenHash);

    Optional<UserSessionEntity> findFirstByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<UserSessionEntity> findByRefreshTokenHash(String refreshTokenHash);
}
