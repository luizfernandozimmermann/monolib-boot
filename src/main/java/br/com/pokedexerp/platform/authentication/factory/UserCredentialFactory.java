package br.com.pokedexerp.platform.authentication.factory;

import br.com.pokedexerp.platform.authentication.model.UserCredentialEntity;

import java.util.UUID;

public interface UserCredentialFactory {
    UserCredentialEntity create(UUID userId, String password);
}
