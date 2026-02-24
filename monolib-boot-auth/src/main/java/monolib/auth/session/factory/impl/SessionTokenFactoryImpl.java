package monolib.auth.session.factory.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.auth.crypto.SessionHasher;
import monolib.auth.properties.AuthProperties;
import monolib.auth.session.factory.SessionTokenFactory;
import monolib.core.model.Session;
import monolib.core.model.User;
import monolib.core.repository.SessionCoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionTokenFactoryImpl implements SessionTokenFactory {

    SessionHasher sessionHasher;

    SessionCoreRepository crudService;

    AuthProperties properties;

    @Override
    @Transactional(readOnly = true)
    public Session create(User user) {
        var session = new Session();
        session.setUser(user);
        session.setExpiresAt(getExpirationDate());
        session.setAccessToken(sessionHasher.createAccessToken(user, session.getExpiresAt()));
        session.setRefreshToken(sessionHasher.createRefreshToken());
        session.setFirstSession(isFirstSession(user));
        return session;
    }

    private boolean isFirstSession(User user) {
        return crudService.findLastSessionOfUser(user)
                .map(Session::isFirstSession)
                .orElse(true);
    }

    private LocalDateTime getExpirationDate() {
        return LocalDateTime.now().plusSeconds(properties.getSessionTokenExpirationTime());
    }

}
