package monolib.data.mapper.impl;

import monolib.data.api.mapper.AutoMapper;
import monolib.data.mapper.MappingExecutor;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
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
    public <E extends EntityBase, D extends EntityDtoBase> E dtoToEntity(D dto, Class<E> entityClass) {
        return executor.executeDtoToEntity(dto, entityClass);
    }

    @Override
    public <E extends EntityBase, D extends EntityDtoBase> void updateEntity(D dto, E entity) {
        executor.executeUpdate(dto, entity);
    }

    @Override
    public <E extends EntityBase, D extends EntityDtoBase> D entityToDto(E entity, Class<D> dtoClass) {
        return executor.executeEntityToDto(entity, dtoClass);
    }

    @Override
    public <E extends EntityBase> Map<String, Object> entityToDto(E entity, Collection<String> fields) {
        return executor.executeEntityToDto(entity, fields);
    }

}

