package monolib.data.api.controller;

import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public abstract class EntityCreateController<E extends EntityBase, D extends EntityDtoBase> extends EntityControllerBase<E, D> {

    @Transactional
    public D create(D dto) {
        var entity = mapper.dtoToEntity(dto, entityClass);
        return mapper.entityToDto(create(entity), dtoClass);
    }

    protected E create(E entity) {
        validate(entity);
        return getRepository().save(entity);
    }

    protected void validate(E entity) {
        // Override this method in subclasses to perform custom validation before saving the entity
    }

}
