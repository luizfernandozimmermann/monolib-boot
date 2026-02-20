package br.com.pokedexerp.platform.authentication.validator.impl;

import br.com.pokedexerp.platform.authentication.service.UserRoleService;
import br.com.pokedexerp.platform.authentication.validator.UserRoleValidator;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleValidatorImpl implements UserRoleValidator {

    UserRoleService service;

    TranslationService translationService;

    @Override
    @Transactional(readOnly = true)
    public void validate(UUID userId, String[] permissions) {
        if (!service.userHasPermission(userId, permissions)) {
            var errorMessage = translationService.getMessage(TranslationConstants.ACCESS_DENIED);
            throw new ServiceException(HttpStatus.FORBIDDEN, errorMessage);
        }
    }

}
