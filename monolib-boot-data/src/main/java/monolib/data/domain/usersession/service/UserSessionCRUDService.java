package monolib.data.domain.usersession.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.base.service.EntityCRUDServiceBase;
import monolib.data.domain.usersession.model.UserSessionEntity;
import monolib.data.domain.usersession.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSessionCRUDService extends EntityCRUDServiceBase<UserSessionEntity> {

    UserSessionRepository repository;

    @Transactional(readOnly = true)
    public void revokeSession(UUID id, LocalDateTime dateTime) {
        repository.revokeSession(id, dateTime);
    }

    @Transactional(readOnly = true)
    public void revokeAllSessionsByUser(UUID userId) {
        repository.revokeAllSessionsByUser(userId);
    }

    @Transactional(readOnly = true)
    public Optional<UserSessionEntity> findByAccessTokenHashAndRevokedAtNull(String accessTokenHash) {
        return repository.findByAccessTokenHashAndRevokedAtNull(accessTokenHash);
    }

    @Transactional(readOnly = true)
    public Optional<UserSessionEntity> findFirstByUserIdOrderByCreatedAtDesc(UUID userId) {
        return repository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public Optional<UserSessionEntity> findByRefreshTokenHash(String refreshTokenHash) {
        return repository.findByRefreshTokenHash(refreshTokenHash);
    }

}
