package monolib.data.mapper;

import monolib.data.mapper.dto.MappingConfig;

import java.lang.reflect.Field;

public interface ValueMapper {
    Object map(Field sourceField, Object value, MappingConfig<?, ?, ?> config);
}

