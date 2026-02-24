package monolib.data.mapper.impl;

import monolib.data.mapper.CollectionMapper;
import monolib.data.mapper.NestedMapper;
import monolib.data.mapper.ValueMapper;
import monolib.data.mapper.dto.MappingConfig;
import monolib.data.base.model.EntityBase;
import monolib.data.utils.TypeUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValueMapperImpl implements ValueMapper {

    NestedMapper nestedMapper;

    CollectionMapper collectionMapper;

    @Override
    public Object map(Field sourceField, Object value, MappingConfig<?, ?, ?> config) {
        if (value == null) {
            return null;
        }

        if (config.getContext().isMaxDepthReached()) {
            return null;
        }

        if (TypeUtils.isSimpleType(value.getClass())) {
            return value;
        }

        if (TypeUtils.isCollection(value.getClass())) {
            return collectionMapper.mapCollection(sourceField, (Collection<?>) value, config);
        }

        if (EntityBase.class.isAssignableFrom(value.getClass())) {
            return nestedMapper.mapNested(sourceField, value, config);
        }

        return value;
    }

}

