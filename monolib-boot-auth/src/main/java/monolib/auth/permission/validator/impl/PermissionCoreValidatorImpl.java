package monolib.auth.permission.validator.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.auth.utils.TranslationConstants;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.repository.PermissionCoreRepository;
import monolib.core.validator.PermissionCoreValidator;
import monolib.core.translation.TranslationService;
import monolib.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionCoreValidatorImpl implements PermissionCoreValidator {

    PermissionCoreRepository repository;

    TranslationService translationService;

    @Override
    public void validate(User user, String[] permissions) {
        if (!repository.userHasPermission(user, permissions)) {
            var errorMessage = translationService.getMessage(TranslationConstants.ACCESS_DENIED);
            throw new BusinessException(ErrorCode.FORBIDDEN, errorMessage);
        }
    }

}
