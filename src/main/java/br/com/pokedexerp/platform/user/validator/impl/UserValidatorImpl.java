package br.com.pokedexerp.platform.user.validator.impl;

import br.com.pokedexerp.platform.authentication.service.UserService;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import br.com.pokedexerp.platform.user.model.UserEntity;
import br.com.pokedexerp.platform.user.validator.UserValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserValidatorImpl implements UserValidator {

    UserService userService;

    TranslationService translationService;

    @Override
    @Transactional(readOnly = true)
    public void validateSameUserOrAdmin(UserEntity user, UUID userRequestId) {
        var userRequest = userService.findById(userRequestId);
        if (!user.equals(userRequest) && !userRequest.isAdmin()) {
            var errorMessage = translationService.getMessage(TranslationConstants.ACCESS_DENIED);
            throw new ServiceException(HttpStatus.FORBIDDEN, errorMessage);
        }
    }

    @Override
    public void validateIsNotAdmin(UserEntity user) {
        if (user.isAdmin()) {
            var errorMessage = translationService.getMessage(TranslationConstants.OPERATION_INVALID_FOR_ADMIN);
            throw new ServiceException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validateNotSameUser(UserEntity user, UUID anotherUserId) {
        if (user.getId().equals(anotherUserId)) {
            var errorMessage = translationService.getMessage(TranslationConstants.OPERATION_INVALID_FOR_SAME_USER);
            throw new ServiceException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

}
