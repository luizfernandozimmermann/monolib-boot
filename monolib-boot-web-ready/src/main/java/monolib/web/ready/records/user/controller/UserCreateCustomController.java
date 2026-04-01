package monolib.web.ready.records.user.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.context.ContextHolder;
import monolib.data.domain.user.model.UserCreateController;
import monolib.data.domain.user.model.UserEntity;
import monolib.web.ready.records.user.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCreateCustomController extends UserCreateController {

    UserValidator validator;

    @Override
    protected void validate(UserEntity entity) {
        super.validate(entity);
        validator.validateIsNotAdmin(entity);
        validator.validateNotSameUser(entity, ContextHolder.get().getUser().getId());
    }

}
