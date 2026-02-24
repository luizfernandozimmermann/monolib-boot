package monolib.data.domain.role.converter;

import lombok.experimental.UtilityClass;
import monolib.core.model.Role;
import monolib.data.domain.role.model.RoleEntity;

@UtilityClass
public class RoleConverter {

    public static RoleEntity convert(Role role) {
        var entity = new RoleEntity();
        entity.setName(role.getName());
        entity.setAdmin(role.isAdmin());
        return entity;
    }

}
