package monolib.data.domain.userrole.factory.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.domain.role.service.RoleCRUDService;
import monolib.data.domain.user.service.UserCRUDService;
import monolib.data.domain.userrole.factory.UserRoleFactory;
import monolib.data.domain.userrole.model.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleFactoryImpl implements UserRoleFactory {

    UserCRUDService userService;

    RoleCRUDService roleService;

    @Override
    @Transactional(readOnly = true)
    public UserRoleEntity create(UUID userId, UUID roleId) {
        var userRole = new UserRoleEntity();
        userService.findById(userId).ifPresent(userRole::setUser);
        roleService.findById(roleId).ifPresent(userRole::setRole);
        return userRole;
    }

}
