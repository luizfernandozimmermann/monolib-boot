package monolib.data.mapper.factory;

import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.data.mapper.dto.EntityToDtoMappingFunction;
import monolib.data.mapper.dto.MappingConfig;
import monolib.data.mapper.dto.MappingContext;
import monolib.data.mapper.dto.UpdateEntityMappingFunction;

import java.util.Collection;

public interface MappingConfigFactory {

    <E extends EntityBase, D extends EntityDtoBase> MappingConfig<E, D, E> updateEntity(
            D source, E target, MappingContext context, UpdateEntityMappingFunction nestedFunction);

    <E extends EntityBase, D> MappingConfig<E, E, D> entityToDto(
            E source, D target, MappingContext context, Collection<String> fields, EntityToDtoMappingFunction nestedFunction);

}
