package monolib.data.domain.userrole.factory.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.domain.role.repository.RoleRepository;
import monolib.data.domain.user.repository.UserRepository;
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

    UserRepository userRepository;

    RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserRoleEntity create(UUID userId, UUID roleId) {
        var userRole = new UserRoleEntity();
        userRepository.findById(userId).ifPresent(userRole::setUser);
        roleRepository.findById(roleId).ifPresent(userRole::setRole);
        return userRole;
    }

}
