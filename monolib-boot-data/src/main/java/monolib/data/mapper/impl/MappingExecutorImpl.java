package monolib.data.mapper.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.data.mapper.FieldMapper;
import monolib.data.mapper.MappingExecutor;
import monolib.data.mapper.dto.MappingContext;
import monolib.data.mapper.factory.MappingConfigFactory;
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
    public <E extends EntityBase, D extends EntityDtoBase> E executeDtoToEntity(D dto, Class<E> entityClass) {
        var entity = entityClass.getDeclaredConstructor().newInstance();
        var context = new MappingContext(MAX_DEPTH);
        return executeDtoToEntity(dto, entity, context);
    }

    private <E extends EntityBase, D extends EntityDtoBase> E executeDtoToEntity(D dto, E entity, MappingContext context) {
        context.register(dto, entity);
        var config = mappingConfigFactory.updateEntity(dto, entity, context, this::executeDtoToEntity);
        config.setIncludeNonUpdatable(true);
        fieldMapper.mapFields(config);
        return entity;
    }

    @Override
    public <E extends EntityBase, D extends EntityDtoBase> void executeUpdate(D dto, E entity) {
        var context = new MappingContext(MAX_DEPTH);
        executeUpdate(dto, entity, context);
    }

    private <E extends EntityBase, D extends EntityDtoBase> E executeUpdate(D dto, E entity, MappingContext context) {
        context.register(dto, entity);
        var config = mappingConfigFactory.updateEntity(dto, entity, context, this::executeUpdate);
        fieldMapper.mapFields(config);
        return entity;
    }

    @Override
    @SneakyThrows
    public <E extends EntityBase, D extends EntityDtoBase> D executeEntityToDto(E entity, Class<D> dtoClass) {
        if (entity == null) {
            return null;
        }
        return dtoClass.getDeclaredConstructor(entity.getClass()).newInstance(entity);
    }

    @Override
    public <E extends EntityBase> Map<String, Object> executeEntityToDto(E entity, Collection<String> fields) {
        var context = new MappingContext(MAX_DEPTH);
        return executeEntityToDto(entity, fields, context);
    }

    private <E extends EntityBase> Map<String, Object> executeEntityToDto(E entity, Collection<String> fields, MappingContext context) {
        if (entity == null) {
            return Map.of();
        }
        var dto = new HashMap<String, Object>();
        context.register(entity, dto);
        var config = mappingConfigFactory.entityToDto(entity, dto, context, fields, this::executeEntityToDto);
        fieldMapper.mapFields(config);
        return dto;
    }

}
