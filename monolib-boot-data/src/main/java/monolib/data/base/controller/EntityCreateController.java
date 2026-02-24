package monolib.data.base.controller;

import jakarta.validation.Valid;
import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public abstract class EntityCreateController<E extends EntityBase, D extends EntityBaseDto> extends EntityControllerBase<E, D> {

    @Transactional
    public D create(@Valid D dto) {
        var entity = mapper.dtoToEntity(dto, entityClass);
        validate(entity);
        entity = repository.save(entity);
        return mapper.entityToDto(entity, dtoClass);
    }

    protected void validate(E entity) {
        // Override this method in subclasses to perform custom validation before saving the entity
    }

}
