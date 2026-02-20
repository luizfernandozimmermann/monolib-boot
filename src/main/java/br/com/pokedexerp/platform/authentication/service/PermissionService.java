package br.com.pokedexerp.platform.authentication.service;

import br.com.pokedexerp.platform.authentication.model.PermissionEntity;
import br.com.pokedexerp.platform.custom.runner.dto.PermissionNode;

import java.util.List;

public interface PermissionService {

    void create(List<PermissionNode> permissions);

    void create(List<PermissionNode> permissions, List<PermissionEntity> allPermissions, PermissionEntity parent);
}
