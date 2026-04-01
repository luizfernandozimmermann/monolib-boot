package monolib.data.domain.userrole.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Role;
import monolib.core.model.User;
import monolib.data.domain.userrole.factory.UserRoleFactory;
import monolib.data.domain.userrole.repository.UserRoleRepository;
import monolib.data.domain.userrole.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleServiceImpl implements UserRoleService {

    UserRoleRepository userRoleRepository;

    UserRoleFactory factory;

    @Override
    @Transactional
    public void grantRole(User user, Role role) {
        var userRole = factory.create(user.getId(), role.getId());
        userRoleRepository.save(userRole);
    }

}
