package monolib.data.base.controller;

import lombok.SneakyThrows;
import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import monolib.data.mapper.metadata.EntityMetadata;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public abstract class EntityDeleteController<E extends EntityBase, D extends EntityBaseDto> extends EntityControllerBase<E, D> {

    @SneakyThrows
    @Transactional
    public void delete(UUID id) {
        var metadata = EntityMetadata.of(entityClass);

        var entity = findById(id);
        validate(entity);
        if (metadata.getSoftDeleteField() != null) {
            var field = metadata.getField(metadata.getSoftDeleteField());
            field.set(entity, false);
            repository.save(entity);
        } else {
            repository.deleteById(id);
        }
    }

    protected void validate(E entity) {
        // Override this method in subclasses to perform custom validation before deleting the entity
    }

}
