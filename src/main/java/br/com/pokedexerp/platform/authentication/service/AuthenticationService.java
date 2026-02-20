package br.com.pokedexerp.platform.authentication.service;

import br.com.pokedexerp.platform.authentication.dto.CreateUserDto;
import br.com.pokedexerp.platform.authentication.dto.SignInDto;
import br.com.pokedexerp.platform.authentication.model.UserSessionDto;

public interface AuthenticationService {
    UserSessionDto authenticateByAccessToken(String accessToken);

    UserSessionDto signIn(SignInDto dto);

    UserSessionDto changePassword(String oldPassword, String newPassword);

    UserSessionDto createFirstUser(CreateUserDto dto);

    UserSessionDto refreshSession();

    void signOut();
}
