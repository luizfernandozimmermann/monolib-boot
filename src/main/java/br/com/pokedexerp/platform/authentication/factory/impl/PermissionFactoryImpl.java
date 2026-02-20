package br.com.pokedexerp.platform.authentication.factory.impl;

import br.com.pokedexerp.platform.authentication.factory.PermissionFactory;
import br.com.pokedexerp.platform.authentication.model.PermissionEntity;
import br.com.pokedexerp.platform.custom.runner.dto.PermissionNode;
import org.springframework.stereotype.Component;

@Component
public class PermissionFactoryImpl implements PermissionFactory {

    @Override
    public PermissionEntity create(PermissionNode permissionNode, PermissionEntity parentPermission) {
        var permission = new PermissionEntity();
        permission.setResource(permissionNode.getResource());
        permission.setDescription(permissionNode.getDescription());
        permission.setParentPermission(parentPermission);
        return permission;
    }

}
