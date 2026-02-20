package br.com.pokedexerp.platform.authentication.service;

import br.com.pokedexerp.platform.authentication.model.PermissionEntity;

import java.util.List;

public interface RolePermissionService {
    void grantPermissionsForAdmin(List<PermissionEntity> permissions);
}
