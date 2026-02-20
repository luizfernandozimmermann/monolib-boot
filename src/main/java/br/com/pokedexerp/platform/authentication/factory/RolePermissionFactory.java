package br.com.pokedexerp.platform.authentication.factory;

import br.com.pokedexerp.platform.authentication.model.PermissionEntity;
import br.com.pokedexerp.platform.authentication.model.RoleEntity;
import br.com.pokedexerp.platform.authentication.model.RolePermissionEntity;

public interface RolePermissionFactory {
    RolePermissionEntity create(RoleEntity role, PermissionEntity permission);
}
