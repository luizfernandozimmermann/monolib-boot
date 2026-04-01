package monolib.data.api.controller;

import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public abstract class EntityUpdateController<E extends EntityBase, D extends EntityDtoBase> extends EntityControllerBase<E, D> {

    @Transactional
    public D update(UUID id, D dto) {
        var entity = findById(id);
        var entityBefore = SerializationUtils.clone(entity);
        mapper.updateEntity(dto, entity);
        return mapper.entityToDto(update(entityBefore, entity), dtoClass);
    }

    protected E update(E entityBefore, E entity) {
        validate(entityBefore, entity);
        return getRepository().save(entity);
    }

    protected void validate(E entityBefore, E entity) {
        // Override this method in subclasses to perform custom validation before saving the entity
    }

}
