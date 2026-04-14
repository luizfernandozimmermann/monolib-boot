package monolib.data.mapper.factory.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.data.mapper.dto.MappingConfig;
import monolib.data.mapper.dto.MappingContext;
import monolib.data.mapper.dto.EntityToDtoMappingFunction;
import monolib.data.mapper.dto.UpdateEntityMappingFunction;
import monolib.data.mapper.factory.MappingConfigFactory;
import monolib.data.mapper.factory.ProjectionEngineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@SuppressWarnings("unchecked")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingConfigFactoryImpl implements MappingConfigFactory {

    ProjectionEngineFactory projectionEngineFactory;

    @Override
    public <E extends EntityBase, D extends EntityDtoBase> MappingConfig<E, D, E> updateEntity(
            D source, E target, MappingContext context, UpdateEntityMappingFunction nestedFunction) {
        return MappingConfig.<E, D, E>builder()
                .entityClass((Class<E>) target.getClass())
                .source(source)
                .target(target)
                .includeNonUpdatable(false)
                .context(context)
                .nestedMappingFunction(nestedFunction)
                .build();
    }

    @Override
    public <E extends EntityBase, D> MappingConfig<E, E, D> entityToDto(
            E source, D target, MappingContext context, Collection<String> fields, EntityToDtoMappingFunction nestedFunction) {
        return MappingConfig.<E, E, D>builder()
                .entityClass((Class<E>) source.getClass())
                .source(source)
                .target(target)
                .projection(projectionEngineFactory.create(fields))
                .includeNonUpdatable(true)
                .context(context)
                .nestedMappingFunction(nestedFunction)
                .build();
    }

}
