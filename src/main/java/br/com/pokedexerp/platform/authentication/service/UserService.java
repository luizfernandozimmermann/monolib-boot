package br.com.pokedexerp.platform.authentication.service;

import br.com.pokedexerp.platform.authentication.dto.CreateUserDto;
import br.com.pokedexerp.platform.user.model.UserDto;
import br.com.pokedexerp.platform.user.model.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity findById(UUID id);

    boolean existsAny();

    UserDto createFirstUser(CreateUserDto dto);
}
