package monolib.data.mapper.factory;

import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import monolib.data.mapper.NestedMappingFunction;
import monolib.data.mapper.dto.MappingConfig;
import monolib.data.mapper.dto.MappingContext;

import java.util.Collection;

public interface MappingConfigFactory {

    <E extends EntityBase, D extends EntityBaseDto> MappingConfig<E, D, E> updateEntity(
            Class<E> entityClass, D source, E target, MappingContext context);

    <E extends EntityBase, D> MappingConfig<E, E, D> entityToDto(
            Class<E> entityClass, E source, D target, MappingContext context, Collection<String> fields, NestedMappingFunction nestedFunction);

}
