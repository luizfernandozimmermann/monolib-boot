package br.com.pokedexerp.platform.user.handler;

import br.com.pokedexerp.platform.entity.handler.EntityCreateHandler;
import br.com.pokedexerp.platform.messaging.annotation.EntityHandler;
import br.com.pokedexerp.platform.user.model.UserDto;
import br.com.pokedexerp.platform.user.model.UserEntity;

@EntityHandler(path = "record/user")
public class UserCreateHandler extends EntityCreateHandler<UserEntity, UserDto> {

}
