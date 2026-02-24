package monolib.data.mapper;

import monolib.data.mapper.dto.MappingConfig;

import java.lang.reflect.Field;
import java.util.Collection;

public interface CollectionMapper {

    Collection<?> mapCollection(Field sourceField, Collection<?> values, MappingConfig<?, ?, ?> config);

}
