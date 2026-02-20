package br.com.pokedexerp.platform.authentication.validator;

import java.util.UUID;

public interface UserRoleValidator {
    void validate(UUID userId, String[] roles);
}
