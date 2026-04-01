package monolib.web.ready.records.user.handler;

import monolib.data.domain.user.model.UserDto;
import monolib.data.domain.user.model.UserEntity;
import monolib.web.api.EntityHandler;
import monolib.web.handler.CreateEntityHandler;

@EntityHandler(path = "record/user")
public class UserCreateHandler extends CreateEntityHandler<UserEntity, UserDto> {

}
