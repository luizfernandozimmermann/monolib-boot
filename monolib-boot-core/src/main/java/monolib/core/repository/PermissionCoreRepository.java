package monolib.core.repository;

import monolib.core.model.Permission;
import monolib.core.model.User;

import java.util.Optional;

public interface PermissionCoreRepository {

    boolean userHasPermission(User user, String[] permissions);

    Optional<Permission> findByResource(String resource);

    Permission save(Permission permission);
}
