package br.com.pokedexerp.platform.authentication.service;

import java.util.UUID;

public interface UserRoleService {
    boolean userHasPermission(UUID userId, String[] roles);

    void grantRole(UUID userId, String role);
}
