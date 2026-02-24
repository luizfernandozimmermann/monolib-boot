package monolib.data.domain.role.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.base.service.EntityCRUDServiceBase;
import monolib.data.domain.role.model.RoleEntity;
import monolib.data.domain.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleCRUDService extends EntityCRUDServiceBase<RoleEntity> {

    RoleRepository repository;

    @Transactional(readOnly = true)
    public Optional<RoleEntity> findByName(String name) {
        return repository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Optional<RoleEntity> findByAdminTrue() {
        return repository.findByAdminTrue();
    }

}
