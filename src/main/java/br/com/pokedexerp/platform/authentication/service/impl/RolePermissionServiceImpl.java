package br.com.pokedexerp.platform.authentication.service.impl;

import br.com.pokedexerp.platform.authentication.factory.RolePermissionFactory;
import br.com.pokedexerp.platform.authentication.model.PermissionEntity;
import br.com.pokedexerp.platform.authentication.model.RoleEntity;
import br.com.pokedexerp.platform.authentication.repository.RolePermissionRepository;
import br.com.pokedexerp.platform.authentication.service.RolePermissionService;
import br.com.pokedexerp.platform.authentication.service.RoleService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolePermissionServiceImpl implements RolePermissionService {

    RoleService roleService;

    RolePermissionRepository repository;

    RolePermissionFactory factory;

    @Override
    @Transactional
    public void grantPermissionsForAdmin(List<PermissionEntity> permissions) {
        var adminRole = roleService.findAdmin();
        permissions.stream()
                .filter(permission -> !repository.existsByRoleAndPermission(adminRole, permission))
                .forEach(permission -> grantPermission(adminRole, permission));
    }

    private void grantPermission(RoleEntity role, PermissionEntity permission) {
        var rolePermission = factory.create(role, permission);
        repository.save(rolePermission);
    }

}
