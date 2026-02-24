package monolib.data.domain.permission.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.base.service.EntityCRUDServiceBase;
import monolib.data.domain.permission.model.PermissionEntity;
import monolib.data.domain.permission.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionCRUDService extends EntityCRUDServiceBase<PermissionEntity> {

    PermissionRepository repository;

    @Transactional(readOnly = true)
    public Optional<PermissionEntity> findByResource(String resource) {
        return repository.findByResource(resource);
    }

}
