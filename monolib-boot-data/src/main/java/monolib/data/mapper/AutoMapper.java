package monolib.data.mapper;

import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;

import java.util.Collection;
import java.util.Map;

public interface AutoMapper {

    <E extends EntityBase, D extends EntityBaseDto> E dtoToEntity(D dto, Class<E> entityClass);

    <E extends EntityBase, D extends EntityBaseDto> void updateEntity(D dto, E entity);

    <E extends EntityBase, D extends EntityBaseDto> D entityToDto(E entity, Class<D> dtoClass);

    <E extends EntityBase> Map<String, Object> entityToDto(E entity, Collection<String> fields);

}

