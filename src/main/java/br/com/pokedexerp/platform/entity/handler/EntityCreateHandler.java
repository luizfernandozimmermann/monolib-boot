package br.com.pokedexerp.platform.entity.handler;

import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import br.com.pokedexerp.platform.messaging.annotation.PostRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public abstract class EntityCreateHandler<E extends BaseEntity, D extends BaseEntityDto> extends BaseEntityHandler<E, D> {

    @PostRequest
    @Transactional
    public D create(@RequestBody @Valid D dto) {
        var entity = mapper.dtoToEntity(dto, entityClass);
        validate(entity);
        entity = repository.save(entity);
        return mapper.entityToDto(entity, dtoClass);
    }

    protected void validate(E entity) {
        // Override this method in subclasses to perform custom validation before saving the entity
    }

}
