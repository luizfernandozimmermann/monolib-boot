package monolib.data.mapper.dto;

import monolib.data.api.model.EntityBase;

import java.util.Collection;
import java.util.Map;

@FunctionalInterface
public interface EntityToDtoMappingFunction extends NestedMappingFunction<EntityBase, Collection<String>, Map<String, Object>> {
}
