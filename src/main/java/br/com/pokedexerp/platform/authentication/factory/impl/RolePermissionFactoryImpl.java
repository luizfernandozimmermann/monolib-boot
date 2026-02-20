package br.com.pokedexerp.platform.authentication.factory.impl;

import br.com.pokedexerp.platform.authentication.factory.RolePermissionFactory;
import br.com.pokedexerp.platform.authentication.model.PermissionEntity;
import br.com.pokedexerp.platform.authentication.model.RoleEntity;
import br.com.pokedexerp.platform.authentication.model.RolePermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionFactoryImpl implements RolePermissionFactory {

    @Override
    public RolePermissionEntity create(RoleEntity role, PermissionEntity permission) {
        var rolePermission = new RolePermissionEntity();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        return rolePermission;
    }

}
