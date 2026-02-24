package monolib.web.ready.records.user.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.context.ContextHolder;
import monolib.data.domain.user.model.UserDto;
import monolib.data.domain.user.model.UserEntity;
import monolib.web.annotation.EntityHandler;
import monolib.web.entity.handler.DeleteEntityHandler;
import monolib.web.ready.records.user.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;

@EntityHandler(path = "record/user")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeleteHandler extends DeleteEntityHandler<UserEntity, UserDto> {

    UserValidator validator;

    @Override
    protected void validate(UserEntity entity) {
        super.validate(entity);
        validator.validateIsNotAdmin(entity);
        validator.validateNotSameUser(entity, ContextHolder.get().getUser().getId());
    }

}
