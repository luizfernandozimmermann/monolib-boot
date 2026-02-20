package br.com.pokedexerp.platform.authentication.validator;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;

public interface AuthenticationValidator {
    void validateNotExpired(UserSessionDto session);

    void validateToken(String token);

    void validateNotRevoked(UserSessionDto session);

    void validateCreateFirstUser();
}
