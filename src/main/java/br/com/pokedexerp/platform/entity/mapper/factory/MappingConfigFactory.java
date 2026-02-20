package br.com.pokedexerp.platform.entity.mapper.factory;

import br.com.pokedexerp.platform.entity.mapper.NestedMappingFunction;
import br.com.pokedexerp.platform.entity.mapper.dto.MappingConfig;
import br.com.pokedexerp.platform.entity.mapper.dto.MappingContext;
import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;

import java.util.Collection;

public interface MappingConfigFactory {

    <E extends BaseEntity, D extends BaseEntityDto> MappingConfig<E, D, E> updateEntity(
            Class<E> entityClass, D source, E target, MappingContext context);

    <E extends BaseEntity, D> MappingConfig<E, E, D> entityToDto(
            Class<E> entityClass, E source, D target, MappingContext context, Collection<String> fields, NestedMappingFunction nestedFunction);

}
