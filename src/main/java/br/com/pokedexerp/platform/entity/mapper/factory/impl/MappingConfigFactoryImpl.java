package br.com.pokedexerp.platform.entity.mapper.factory.impl;

import br.com.pokedexerp.platform.entity.mapper.NestedMappingFunction;
import br.com.pokedexerp.platform.entity.mapper.dto.MappingConfig;
import br.com.pokedexerp.platform.entity.mapper.dto.MappingContext;
import br.com.pokedexerp.platform.entity.mapper.factory.MappingConfigFactory;
import br.com.pokedexerp.platform.entity.mapper.factory.ProjectionEngineFactory;
import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
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
    public <E extends BaseEntity, D extends BaseEntityDto> MappingConfig<E, D, E> updateEntity(
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
    public <E extends BaseEntity, D> MappingConfig<E, E, D> entityToDto(
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
