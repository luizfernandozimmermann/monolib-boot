package monolib.data.mapper;

import monolib.data.mapper.dto.MappingConfig;

import java.lang.reflect.Field;

public interface NestedMapper {
    Object mapNested(Field sourceField, Object value, MappingConfig<?, ?, ?> config);
}
