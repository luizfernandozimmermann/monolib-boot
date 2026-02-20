package br.com.pokedexerp.platform.entity.mapper.impl;

import br.com.pokedexerp.platform.entity.mapper.AutoMapper;
import br.com.pokedexerp.platform.entity.mapper.MappingExecutor;
import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AutoMapperImpl implements AutoMapper {

    MappingExecutor executor;

    @Override
    public <E extends BaseEntity, D extends BaseEntityDto> E dtoToEntity(D dto, Class<E> entityClass) {
        return executor.executeDtoToEntity(dto, entityClass);
    }

    @Override
    public <E extends BaseEntity, D extends BaseEntityDto> void updateEntity(D dto, E entity) {
        executor.executeUpdate(dto, entity);
    }

    @Override
    public <E extends BaseEntity, D extends BaseEntityDto> D entityToDto(E entity, Class<D> dtoClass) {
        return executor.executeEntityToDto(entity, dtoClass);
    }

    @Override
    public <E extends BaseEntity> Map<String, Object> entityToDto(E entity, Collection<String> fields) {
        return executor.executeEntityToDto(entity, fields);
    }

}

