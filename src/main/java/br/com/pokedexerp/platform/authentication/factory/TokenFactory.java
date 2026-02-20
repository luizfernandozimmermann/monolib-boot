package br.com.pokedexerp.platform.authentication.factory;

import java.time.LocalDateTime;

public interface TokenFactory {

    String createAccessToken(String userId, LocalDateTime expiresAt);
    String createRefreshToken();

}
