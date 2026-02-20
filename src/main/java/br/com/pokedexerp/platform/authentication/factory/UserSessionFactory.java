package br.com.pokedexerp.platform.authentication.factory;

import br.com.pokedexerp.platform.authentication.model.UserSessionEntity;
import br.com.pokedexerp.platform.user.model.UserDto;

public interface UserSessionFactory {
    UserSessionEntity create(UserDto user);
}
