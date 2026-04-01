package monolib.data.mapper.factory.impl;

import monolib.data.mapper.NestedMappingFunction;
import monolib.data.mapper.dto.MappingConfig;
import monolib.data.mapper.dto.MappingContext;
import monolib.data.mapper.factory.MappingConfigFactory;
import monolib.data.mapper.factory.ProjectionEngineFactory;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MappingConfigFactoryImpl implements MappingConfigFactory {

    ProjectionEngineFactory projectionEngineFactory;

    @Override
    public <E extends EntityBase, D extends EntityDtoBase> MappingConfig<E, D, E> updateEntity(
            Class<E> entityClass, D source, E target, MappingContext context) {
        return MappingConfig.<E, D, E>builder()
                .entityClass(entityClass)
                .source(source)
                .target(target)
                .includeNonUpdatable(false)
                .context(context)
                .build();
    }

    @Override
    public <E extends EntityBase, D> MappingConfig<E, E, D> entityToDto(
            Class<E> entityClass, E source, D target, MappingContext context, Collection<String> fields, NestedMappingFunction nestedFunction) {
        return MappingConfig.<E, E, D>builder()
                .entityClass(entityClass)
                .source(source)
                .target(target)
                .projection(projectionEngineFactory.create(fields))
                .includeNonUpdatable(true)
                .context(context)
                .nestedMappingFunction(nestedFunction)
                .build();
    }

}
