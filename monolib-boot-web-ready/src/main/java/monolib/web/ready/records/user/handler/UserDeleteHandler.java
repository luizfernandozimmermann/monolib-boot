package monolib.web.ready.records.user.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.domain.user.model.UserDto;
import monolib.data.domain.user.model.UserEntity;
import monolib.web.api.EntityHandler;
import monolib.web.handler.DeleteEntityHandler;
import org.springframework.beans.factory.annotation.Autowired;

@EntityHandler(path = "record/user")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeleteHandler extends DeleteEntityHandler<UserEntity, UserDto> {

}
