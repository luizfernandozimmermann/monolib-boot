package br.com.pokedexerp.platform.entity.handler;

import br.com.pokedexerp.platform.entity.mapper.metadata.EntityMetadata;
import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import br.com.pokedexerp.platform.messaging.annotation.DeleteRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public abstract class EntityDeleteHandler<E extends BaseEntity, D extends BaseEntityDto> extends BaseEntityHandler<E, D> {

    @SneakyThrows
    @Transactional
    @DeleteRequest("/{id}")
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
