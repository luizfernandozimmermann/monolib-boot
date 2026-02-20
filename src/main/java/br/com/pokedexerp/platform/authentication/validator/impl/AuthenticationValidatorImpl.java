package br.com.pokedexerp.platform.authentication.validator.impl;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import br.com.pokedexerp.platform.authentication.service.UserService;
import br.com.pokedexerp.platform.authentication.service.UserSessionService;
import br.com.pokedexerp.platform.authentication.validator.AuthenticationValidator;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import br.com.pokedexerp.platform.utils.EnvironmentConstants;
import br.com.pokedexerp.platform.utils.EnvironmentUtils;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationValidatorImpl implements AuthenticationValidator {

    TranslationService translationService;

    UserSessionService sessionService;

    UserService userService;

    @Override
    public void validateNotExpired(UserSessionDto session) {
        if (session.isExpired()) {
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new ServiceException(HttpStatus.UNAUTHORIZED, errorMessage);
        }
    }

    @Override
    public void validateToken(String token) {
        if (StringUtils.isEmpty(token)) {
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new ServiceException(HttpStatus.UNAUTHORIZED, errorMessage);
        }
    }

    @Override
    public void validateNotRevoked(UserSessionDto session) {
        if (session.isRevoked()) {
            sessionService.revokeAllSessionsByUser(session.getUser().getId());
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new ServiceException(HttpStatus.UNAUTHORIZED, errorMessage);
        }
    }

    @Override
    public void validateCreateFirstUser() {
        if (!EnvironmentUtils.getBoolean(EnvironmentConstants.BOOTSTRAP_ENABLED) || userService.existsAny()) {
            var errorMessage = translationService.getMessage(TranslationConstants.USER_BASE_ALREADY_INITIALIZED);
            throw new ServiceException(HttpStatus.FORBIDDEN, errorMessage);
        }
    }
}
