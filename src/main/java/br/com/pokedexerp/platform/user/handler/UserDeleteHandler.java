package br.com.pokedexerp.platform.user.handler;

import br.com.pokedexerp.platform.entity.handler.EntityDeleteHandler;
import br.com.pokedexerp.platform.messaging.annotation.EntityHandler;
import br.com.pokedexerp.platform.messaging.context.ServiceContext;
import br.com.pokedexerp.platform.user.model.UserDto;
import br.com.pokedexerp.platform.user.model.UserEntity;
import br.com.pokedexerp.platform.user.validator.UserValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

@EntityHandler(path = "record/user")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeleteHandler extends EntityDeleteHandler<UserEntity, UserDto> {

    UserValidator validator;

    @Override
    protected void validate(UserEntity entity) {
        super.validate(entity);
        validator.validateIsNotAdmin(entity);
        validator.validateNotSameUser(entity, ServiceContext.getUser().getId());
    }

}
