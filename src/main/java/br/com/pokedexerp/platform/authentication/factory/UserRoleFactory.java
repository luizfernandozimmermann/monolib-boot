package br.com.pokedexerp.platform.authentication.factory;

import br.com.pokedexerp.platform.authentication.model.UserRoleEntity;

import java.util.UUID;

public interface UserRoleFactory {
    UserRoleEntity create(UUID userId, String role);
}
