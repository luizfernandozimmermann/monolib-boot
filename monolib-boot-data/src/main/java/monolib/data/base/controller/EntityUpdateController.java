package monolib.data.base.controller;

import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public abstract class EntityUpdateController<E extends EntityBase, D extends EntityBaseDto> extends EntityControllerBase<E, D> {

    @Transactional
    public D update(UUID id, D dto) {
        var entity = findById(id);
        var entityBefore = SerializationUtils.clone(entity);
        mapper.updateEntity(dto, entity);
        validate(entityBefore, entity);
        repository.save(entity);
        return mapper.entityToDto(entity, dtoClass);
    }

    protected void validate(E entityBefore, E entity) {
        // Override this method in subclasses to perform custom validation before saving the entity
    }

}
