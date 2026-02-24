package monolib.data.domain.userrole.service;

import monolib.core.model.Role;
import monolib.core.model.User;

public interface UserRoleService {
    void grantRole(User userId, Role role);
}
