package monolib.data.domain.userrole.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.base.service.EntityCRUDServiceBase;
import monolib.data.domain.userrole.model.UserRoleEntity;
import monolib.data.domain.userrole.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleCRUDService extends EntityCRUDServiceBase<UserRoleEntity> {

    UserRoleRepository repository;

    @Transactional(readOnly = true)
    public boolean userHasPermission(UUID userId, String[] permission) {
        return repository.userHasPermission(userId, permission);
    }

}
