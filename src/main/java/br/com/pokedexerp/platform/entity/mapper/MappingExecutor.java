package br.com.pokedexerp.platform.entity.mapper;

import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;

import java.util.Collection;
import java.util.Map;

public interface MappingExecutor {

    <E extends BaseEntity, D extends BaseEntityDto> E executeDtoToEntity(D dto, Class<E> entityClass);

    <E extends BaseEntity, D extends BaseEntityDto> void executeUpdate(D dto, E entity);

    <E extends BaseEntity, D extends BaseEntityDto> D executeEntityToDto(E entity, Class<D> dtoClass);

    <E extends BaseEntity> Map<String, Object> executeEntityToDto(E entity, Collection<String> fields);

}
