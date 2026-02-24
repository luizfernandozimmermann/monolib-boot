package monolib.data.domain.userrole.factory;

import monolib.data.domain.userrole.model.UserRoleEntity;

import java.util.UUID;

public interface UserRoleFactory {
    UserRoleEntity create(UUID userId, UUID roleId);
}
