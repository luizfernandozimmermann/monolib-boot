package monolib.data.domain.usersession.converter.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Session;
import monolib.data.domain.user.converter.UserConverter;
import monolib.data.domain.user.repository.UserRepository;
import monolib.data.domain.usersession.converter.UserSessionConverter;
import monolib.data.domain.usersession.model.UserSessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSessionConverterImpl implements UserSessionConverter {

    UserRepository userRepository;

    @Override
    public Session convert(UserSessionEntity entity) {
        var session = new Session();
        session.setId(entity.getId());
        session.setUser(UserConverter.convert(entity.getUser()));
        session.setExpiresAt(entity.getExpiresAt());
        session.setRevokedAt(entity.getRevokedAt());
        session.setAccessToken(entity.getAccessTokenHash());
        session.setRefreshToken(entity.getRefreshTokenHash());
        session.setFirstSession(entity.isFirstSession());
        return session;
    }

    @Override
    @Transactional(readOnly = true)
    public UserSessionEntity convert(UserSessionEntity entity, Session session) {
        entity.setId(session.getId());
        userRepository.findById(session.getUser().getId()).ifPresent(entity::setUser);
        entity.setExpiresAt(session.getExpiresAt());
        entity.setRevokedAt(session.getRevokedAt());
        entity.setAccessTokenHash(session.getAccessToken());
        entity.setRefreshTokenHash(session.getRefreshToken());
        entity.setFirstSession(session.isFirstSession());
        return entity;
    }

}
