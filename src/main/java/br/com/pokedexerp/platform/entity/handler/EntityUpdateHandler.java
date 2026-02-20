package br.com.pokedexerp.platform.entity.handler;

import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import br.com.pokedexerp.platform.messaging.annotation.PutRequest;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public abstract class EntityUpdateHandler<E extends BaseEntity, D extends BaseEntityDto> extends BaseEntityHandler<E, D> {

    @Transactional
    @PutRequest("/{id}")
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
