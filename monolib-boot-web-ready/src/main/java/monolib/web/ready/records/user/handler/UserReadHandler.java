package monolib.web.ready.records.user.handler;

import monolib.data.domain.user.model.UserDto;
import monolib.data.domain.user.model.UserEntity;
import monolib.web.annotation.EntityHandler;
import monolib.web.entity.handler.ReadEntityHandler;

@EntityHandler(path = "record/user")
public class UserReadHandler extends ReadEntityHandler<UserEntity, UserDto> {
}
