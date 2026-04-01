package monolib.data.api.controller;

import lombok.SneakyThrows;
import monolib.data.api.mapper.EntityMetadata;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public abstract class EntityDeleteController<E extends EntityBase, D extends EntityDtoBase> extends EntityControllerBase<E, D> {

    @SneakyThrows
    @Transactional
    public void delete(UUID id) {
        var metadata = EntityMetadata.of(entityClass);

        var entity = findById(id);
        validate(entity);
        if (metadata.getSoftDeleteField() != null) {
            var field = metadata.getField(metadata.getSoftDeleteField());
            field.set(entity, false);
            getRepository().save(entity);
        } else {
            getRepository().deleteById(id);
        }
    }

    protected void validate(E entity) {
        // Override this method in subclasses to perform custom validation before deleting the entity
    }

}
