package br.com.pokedexerp.platform.custom.runner;

import br.com.pokedexerp.platform.authentication.model.RoleEntity;
import br.com.pokedexerp.platform.authentication.service.PermissionService;
import br.com.pokedexerp.platform.authentication.service.RoleService;
import br.com.pokedexerp.platform.custom.runner.dto.RolePermissionSeed;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Order(2)
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlatformRolePermissionFromFilesRunner implements ApplicationRunner {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    RoleService roleService;

    PermissionService permissionService;

    ResourcePatternResolver resolver;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) throws Exception {
        var files = resolver.getResources("classpath:permissions/*.json");
        for (var file : files) {
            insertFromFile(file);
        }
    }

    private void insertFromFile(Resource resource) throws IOException {
        var seeds = objectMapper.readValue(
                resource.getInputStream(),
                RolePermissionSeed.class
        );

        insertRoles(seeds.getRoles());
        permissionService.create(seeds.getPermissions());
    }

    private void insertRoles(List<String> roles) {
        roles.forEach(this::insertRole);
    }

    private void insertRole(String roleSeed) {
        roleService.findByNameOpt(roleSeed).orElseGet(() -> {
            var role = new RoleEntity();
            role.setName(roleSeed);
            role.setAdmin(roleSeed.equals("ADMIN"));
            return roleService.save(role);
        });
    }

}
