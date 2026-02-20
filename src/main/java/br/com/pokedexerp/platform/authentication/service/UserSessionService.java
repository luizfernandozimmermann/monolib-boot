package br.com.pokedexerp.platform.authentication.service;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import br.com.pokedexerp.platform.user.model.UserDto;

import java.util.UUID;

public interface UserSessionService {
    UserSessionDto findByAccessToken(String accessToken);

    UserSessionDto findByRefreshToken(String refreshToken);

    void revokeSession(UUID id);

    UserSessionDto createSession(UserDto user);

    UserSessionDto createSessionNotFirst(UserDto user);

    void revokeAllSessionsByUser(UUID userId);
}
