package monolib.data.domain.role.converter;

import lombok.experimental.UtilityClass;
import monolib.core.model.Role;
import monolib.data.domain.role.model.RoleEntity;

@UtilityClass
public class RoleConverter {

    public static RoleEntity convert(Role role) {
        var entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setName(role.getName());
        entity.setAdmin(role.isAdmin());
        return entity;
    }

    public static Role convert(RoleEntity entity) {
        var role = new Role();
        role.setId(entity.getId());
        role.setName(entity.getName());
        role.setAdmin(entity.isAdmin());
        return role;
    }

}
