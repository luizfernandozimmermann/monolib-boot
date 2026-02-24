package monolib.data.domain.usersession.repository.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Session;
import monolib.core.repository.SessionCoreRepository;
import monolib.core.model.User;
import monolib.data.domain.usersession.converter.UserSessionConverter;
import monolib.data.domain.usersession.model.UserSessionEntity;
import monolib.data.domain.usersession.service.UserSessionCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionCoreRepositoryImpl implements SessionCoreRepository {

    UserSessionCRUDService userSessionCRUDService;

    UserSessionConverter converter;

    @Override
    public Optional<Session> findByAccessTokenAndNotRevoked(String accessToken) {
        return userSessionCRUDService.findByAccessTokenHashAndRevokedAtNull(accessToken).map(converter::convert);
    }

    @Override
    public Optional<Session> findByRefreshToken(String refreshToken) {
        return userSessionCRUDService.findByRefreshTokenHash(refreshToken).map(converter::convert);
    }

    @Override
    public void revokeSession(Session session, LocalDateTime revokedAt) {
        userSessionCRUDService.revokeSession(session.getId(), revokedAt);
    }

    @Override
    public void revokeAllSessionsByUser(User user) {
        userSessionCRUDService.revokeAllSessionsByUser(user.getId());
    }

    @Override
    public Session save(Session session) {
        var entity = Optional.ofNullable(session.getId())
                .flatMap(userSessionCRUDService::findById)
                .orElseGet(UserSessionEntity::new);
        return converter.convert(userSessionCRUDService.save(converter.convert(entity, session)));
    }

    @Override
    public Optional<Session> findLastSessionOfUser(User user) {
        return userSessionCRUDService.findFirstByUserIdOrderByCreatedAtDesc(user.getId()).map(converter::convert);
    }
}
