package monolib.auth.session.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.auth.crypto.SessionHasher;
import monolib.auth.session.factory.SessionTokenFactory;
import monolib.auth.session.service.SessionService;
import monolib.auth.utils.TranslationConstants;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.model.Session;
import monolib.core.model.User;
import monolib.core.repository.SessionCoreRepository;
import monolib.core.translation.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionServiceImpl implements SessionService {

    SessionCoreRepository repository;

    TranslationService translationService;

    SessionTokenFactory factory;

    SessionHasher sessionHasher;

    @Override
    @Transactional(readOnly = true)
    public Session findByAccessToken(String accessToken) {
        return repository.findByAccessTokenAndNotRevoked(sessionHasher.hash(accessToken)).orElseThrow(() -> {
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            return new BusinessException(ErrorCode.UNAUTHORIZED, errorMessage);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Session findByRefreshToken(String refreshToken) {
        return repository.findByRefreshToken(sessionHasher.hash(refreshToken)).orElseThrow(() -> {
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            return new BusinessException(ErrorCode.UNAUTHORIZED, errorMessage);
        });
    }

    @Override
    @Transactional
    public void revokeSession(Session session) {
        repository.revokeSession(session, LocalDateTime.now());
    }

    @Override
    @Transactional
    public Session createSession(User user) {
        var session = factory.create(user);
        return save(session);
    }

    private Session save(Session session) {
        var rawAccessToken = session.getAccessToken();
        var rawRefreshToken = session.getRefreshToken();
        hashTokens(session);
        session = repository.save(session);
        restoreRawTokens(session, rawAccessToken, rawRefreshToken);
        return session;
    }

    private void hashTokens(Session session) {
        session.setAccessToken(sessionHasher.hash(session.getAccessToken()));
        session.setRefreshToken(sessionHasher.hash(session.getRefreshToken()));
    }

    private void restoreRawTokens(Session session, String accessToken, String refreshToken) {
        session.setAccessToken(accessToken);
        session.setRefreshToken(refreshToken);
    }

    @Override
    @Transactional
    public Session createSession(User user, boolean firstSession) {
        var session = factory.create(user);
        session.setFirstSession(firstSession);
        return save(session);
    }

    @Override
    @Transactional
    public void revokeAllUserSessions(User user) {
        repository.revokeAllSessionsByUser(user);
    }

}
