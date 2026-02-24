package monolib.infra.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Role;
import monolib.core.runner.ApplicationStartedRunner;
import monolib.core.service.PermissionCoreService;
import monolib.core.service.RoleCoreService;
import monolib.infra.runner.dto.RolePermissionSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Order(1)
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MonolibRolePermissionFromFilesRunner implements ApplicationStartedRunner {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    RoleCoreService roleService;

    PermissionCoreService permissionService;

    ResourcePatternResolver resolver;

    @Override
    @Transactional
    public void run() {
        var files = getFiles();
        for (var file : files) {
            insertFromFile(file);
        }
    }

    private Resource[] getFiles() {
        try {
            return resolver.getResources("classpath:permissions/*.json");
        } catch (Exception _) {
            return new Resource[0];
        }
    }

    @SneakyThrows
    private void insertFromFile(Resource resource) {
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
        var role = Role.builder()
                .name(roleSeed)
                .admin(roleSeed.equals("ADMIN"))
                .build();
        roleService.create(role);
    }

}
