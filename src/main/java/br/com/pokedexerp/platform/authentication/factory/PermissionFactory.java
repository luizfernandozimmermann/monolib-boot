package br.com.pokedexerp.platform.authentication.factory;

import br.com.pokedexerp.platform.authentication.model.PermissionEntity;
import br.com.pokedexerp.platform.custom.runner.dto.PermissionNode;

public interface PermissionFactory {
    PermissionEntity create(PermissionNode permissionNode, PermissionEntity parentPermission);
}
