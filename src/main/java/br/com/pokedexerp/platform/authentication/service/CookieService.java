package br.com.pokedexerp.platform.authentication.service;

import br.com.pokedexerp.platform.authentication.model.UserSessionEntity;

public interface CookieService {
    void setCookies(UserSessionEntity session);
    void clearCookies();
    void clearAccess();
}
