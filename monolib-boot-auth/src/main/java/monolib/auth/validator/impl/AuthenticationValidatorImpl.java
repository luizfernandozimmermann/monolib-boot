package monolib.auth.validator.impl;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.auth.session.service.SessionService;
import monolib.auth.user.service.UserService;
import monolib.auth.utils.TranslationConstants;
import monolib.auth.validator.AuthenticationValidator;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.model.Session;
import monolib.core.translation.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationValidatorImpl implements AuthenticationValidator {

    TranslationService translationService;

    SessionService sessionService;

    UserService userService;

    @Override
    public void validateNotExpired(Session session) {
        if (session.isExpired()) {
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new BusinessException(ErrorCode.UNAUTHORIZED, errorMessage);
        }
    }

    @Override
    public void validateTokenInformed(String token) {
        if (StringUtils.isEmpty(token)) {
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new BusinessException(ErrorCode.UNAUTHORIZED, errorMessage);
        }
    }

    @Override
    public void validateNotRevoked(Session session) {
        if (session.isRevoked()) {
            sessionService.revokeAllUserSessions(session.getUser());
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new BusinessException(ErrorCode.UNAUTHORIZED, errorMessage);
        }
    }

    @Override
    public void validateCreateFirstUser() {
        if (userService.existsAny()) {
            var errorMessage = translationService.getMessage(TranslationConstants.USER_BASE_ALREADY_INITIALIZED);
            throw new BusinessException(ErrorCode.FORBIDDEN, errorMessage);
        }
    }

    @Override
    public void validateIsNotFirstSession(Session session) {
        if (session.isFirstSession()) {
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new BusinessException(ErrorCode.UNAUTHORIZED, errorMessage);
        }
    }

    @Override
    public void validateSessionInformed(Session session) {
        if (session == null) {
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new BusinessException(ErrorCode.UNAUTHORIZED, errorMessage);
        }
    }
}
