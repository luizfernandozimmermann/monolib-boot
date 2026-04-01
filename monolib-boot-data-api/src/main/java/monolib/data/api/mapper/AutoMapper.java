package monolib.data.api.mapper;

import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;

import java.util.Collection;
import java.util.Map;

public interface AutoMapper {
    <E extends EntityBase, D extends EntityDtoBase> E dtoToEntity(D dto, Class<E> entityClass);

    <E extends EntityBase, D extends EntityDtoBase> void updateEntity(D dto, E entity);

    <E extends EntityBase, D extends EntityDtoBase> D entityToDto(E entity, Class<D> dtoClass);

    <E extends EntityBase> Map<String, Object> entityToDto(E entity, Collection<String> fields);
}
