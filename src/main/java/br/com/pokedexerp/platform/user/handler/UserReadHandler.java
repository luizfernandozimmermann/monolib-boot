package br.com.pokedexerp.platform.user.handler;

import br.com.pokedexerp.platform.messaging.annotation.EntityHandler;
import br.com.pokedexerp.platform.entity.handler.EntityReadHandler;
import br.com.pokedexerp.platform.user.model.UserDto;
import br.com.pokedexerp.platform.user.model.UserEntity;

@EntityHandler(path = "record/user")
public class UserReadHandler extends EntityReadHandler<UserEntity, UserDto> {
}
