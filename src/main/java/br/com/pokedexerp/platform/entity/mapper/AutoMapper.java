package br.com.pokedexerp.platform.entity.mapper;

import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;

import java.util.Collection;
import java.util.Map;

public interface AutoMapper {

    <E extends BaseEntity, D extends BaseEntityDto> E dtoToEntity(D dto, Class<E> entityClass);

    <E extends BaseEntity, D extends BaseEntityDto> void updateEntity(D dto, E entity);

    <E extends BaseEntity, D extends BaseEntityDto> D entityToDto(E entity, Class<D> dtoClass);

    <E extends BaseEntity> Map<String, Object> entityToDto(E entity, Collection<String> fields);

}

