package br.com.pokedexerp.platform.authentication.factory;

import br.com.pokedexerp.platform.authentication.dto.CreateUserDto;
import br.com.pokedexerp.platform.user.model.UserEntity;

public interface UserFactory {
    UserEntity create(CreateUserDto dto);
}
