package monolib.data.mapper.dto;

import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;

@FunctionalInterface
public interface UpdateEntityMappingFunction extends NestedMappingFunction<EntityDtoBase, EntityBase, EntityBase> {
}
