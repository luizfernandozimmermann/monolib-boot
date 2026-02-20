package br.com.pokedexerp.platform.entity.mapper.impl;

import br.com.pokedexerp.platform.entity.mapper.FieldMapper;
import br.com.pokedexerp.platform.entity.mapper.MappingExecutor;
import br.com.pokedexerp.platform.entity.mapper.dto.MappingContext;
import br.com.pokedexerp.platform.entity.mapper.factory.MappingConfigFactory;
import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingExecutorImpl implements MappingExecutor {

    private static final int MAX_DEPTH = 3;

    FieldMapper fieldMapper;

    MappingConfigFactory mappingConfigFactory;

    @Override
    @SneakyThrows
    public <E extends BaseEntity, D extends BaseEntityDto> E executeDtoToEntity(D dto, Class<E> entityClass) {
        var entity = entityClass.getDeclaredConstructor().newInstance();
        var context = new MappingContext(MAX_DEPTH);
        context.register(dto, entity);
        var config = mappingConfigFactory.updateEntity(entityClass, dto, entity, context);
        config.setIncludeNonUpdatable(true);
        fieldMapper.mapFields(config);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends BaseEntity, D extends BaseEntityDto> void executeUpdate(D dto, E entity) {
        var context = new MappingContext(MAX_DEPTH);
        context.register(dto, entity);
        var config = mappingConfigFactory.updateEntity((Class<E>) entity.getClass(), dto, entity, context);
        fieldMapper.mapFields(config);
    }

    @Override
    @SneakyThrows
    public <E extends BaseEntity, D extends BaseEntityDto> D executeEntityToDto(E entity, Class<D> dtoClass) {
        if (entity == null) {
            return null;
        }
        return dtoClass.getDeclaredConstructor(entity.getClass()).newInstance(entity);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <E extends BaseEntity> Map<String, Object> executeEntityToDto(E entity, Collection<String> fields) {
        if (entity == null) {
            return Map.of();
        }
        var dto = new HashMap<String, Object>();
        MappingContext context = new MappingContext(MAX_DEPTH);
        context.register(entity, dto);
        var config = mappingConfigFactory.entityToDto((Class<E>) entity.getClass(), entity, dto, context, fields, this::executeEntityToDto);
        fieldMapper.mapFields(config);
        return dto;
    }

}
