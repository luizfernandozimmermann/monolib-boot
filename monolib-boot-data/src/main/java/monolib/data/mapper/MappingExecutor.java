package monolib.data.mapper;

import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;

import java.util.Collection;
import java.util.Map;

public interface MappingExecutor {

    <E extends EntityBase, D extends EntityDtoBase> E executeDtoToEntity(D dto, Class<E> entityClass);

    <E extends EntityBase, D extends EntityDtoBase> void executeUpdate(D dto, E entity);

    <E extends EntityBase, D extends EntityDtoBase> D executeEntityToDto(E entity, Class<D> dtoClass);

    <E extends EntityBase> Map<String, Object> executeEntityToDto(E entity, Collection<String> fields);

}
