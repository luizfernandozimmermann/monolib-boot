package monolib.core.service;

import monolib.core.model.Permission;
import monolib.core.model.Role;
import monolib.core.model.User;

import java.util.List;

public interface RoleCoreService {
    void grantRole(User user, Role role);

    void create(Role role);
    void grantPermissionsForAdmin(List<Permission> permissions);
}
