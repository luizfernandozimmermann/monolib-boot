package monolib.web.ready.records.user.validator.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.model.User;
import monolib.core.translation.TranslationService;
import monolib.data.domain.user.model.UserEntity;
import monolib.web.ready.records.user.validator.UserValidator;
import monolib.web.ready.utils.TranslationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserValidatorImpl implements UserValidator {

    TranslationService translationService;

    @Override
    @Transactional(readOnly = true)
    public void validateSameUserOrAdmin(UserEntity user, User userRequest) {
        if (!user.getId().equals(userRequest.getId()) && !userRequest.isAdmin()) {
            var errorMessage = translationService.getMessage(monolib.auth.utils.TranslationConstants.ACCESS_DENIED);
            throw new BusinessException(ErrorCode.FORBIDDEN, errorMessage);
        }
    }

    @Override
    public void validateIsNotAdmin(UserEntity user) {
        if (user.isAdmin()) {
            var errorMessage = translationService.getMessage(TranslationConstants.OPERATION_INVALID_FOR_ADMIN);
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, errorMessage);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validateNotSameUser(UserEntity user, UUID anotherUserId) {
        if (user.getId().equals(anotherUserId)) {
            var errorMessage = translationService.getMessage(TranslationConstants.OPERATION_INVALID_FOR_SAME_USER);
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, errorMessage);
        }
    }

}
