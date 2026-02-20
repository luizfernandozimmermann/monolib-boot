package br.com.pokedexerp.platform.authentication.service.impl;

import br.com.pokedexerp.platform.authentication.factory.PermissionFactory;
import br.com.pokedexerp.platform.authentication.model.PermissionEntity;
import br.com.pokedexerp.platform.authentication.repository.PermissionRepository;
import br.com.pokedexerp.platform.authentication.service.PermissionService;
import br.com.pokedexerp.platform.authentication.service.RolePermissionService;
import br.com.pokedexerp.platform.custom.runner.dto.PermissionNode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository repository;

    PermissionFactory factory;

    RolePermissionService rolePermissionService;

    @Override
    @Transactional
    public void create(List<PermissionNode> permissions) {
        var allPermissions = new ArrayList<PermissionEntity>();
        this.create(permissions, allPermissions, null);
        rolePermissionService.grantPermissionsForAdmin(allPermissions);
    }

    @Override
    @Transactional
    public void create(List<PermissionNode> permissions, List<PermissionEntity> allPermissions, PermissionEntity parent) {
        permissions.forEach(permissionSeed -> create(permissionSeed, allPermissions, parent));
    }

    private void create(PermissionNode permissionNode, List<PermissionEntity> allPermissions, PermissionEntity parentPermission) {
        repository.findByResource(permissionNode.getResource()).ifPresentOrElse(allPermissions::add, () -> {
            var permission = repository.save(factory.create(permissionNode, parentPermission));
            allPermissions.add(permission);
            create(permissionNode.getChildren(), allPermissions, permission);
        });
    }

}
