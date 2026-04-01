package monolib.data.mapper;

import monolib.data.api.model.EntityBase;

import java.util.Collection;
import java.util.Map;

@FunctionalInterface
public interface NestedMappingFunction {
    Map<String, Object> map(EntityBase entity, Collection<String> fields);
}
