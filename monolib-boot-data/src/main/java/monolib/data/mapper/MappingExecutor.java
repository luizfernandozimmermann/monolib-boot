package monolib.data.mapper;

import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;

import java.util.Collection;
import java.util.Map;

public interface MappingExecutor {

    <E extends EntityBase, D extends EntityBaseDto> E executeDtoToEntity(D dto, Class<E> entityClass);

    <E extends EntityBase, D extends EntityBaseDto> void executeUpdate(D dto, E entity);

    <E extends EntityBase, D extends EntityBaseDto> D executeEntityToDto(E entity, Class<D> dtoClass);

    <E extends EntityBase> Map<String, Object> executeEntityToDto(E entity, Collection<String> fields);

}
