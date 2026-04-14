package monolib.data.mapper.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.data.mapper.CollectionMapper;
import monolib.data.mapper.NestedMapper;
import monolib.data.mapper.ValueMapper;
import monolib.data.mapper.dto.FieldMappingContext;
import monolib.data.utils.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValueMapperImpl implements ValueMapper {

    NestedMapper nestedMapper;

    CollectionMapper collectionMapper;

    @Override
    public Object map(FieldMappingContext fieldContext) {
        var value = fieldContext.getSourceValue();
        if (value == null) {
            return null;
        }

        if (fieldContext.isMaxDepthReached()) {
            return null;
        }

        if (TypeUtils.isSimpleType(value.getClass())) {
            return value;
        }

        if (TypeUtils.isCollection(value.getClass())) {
            return collectionMapper.mapCollection(fieldContext);
        }

        if (EntityDtoBase.class.isAssignableFrom(value.getClass())
                || EntityBase.class.isAssignableFrom(value.getClass())) {
            return nestedMapper.mapNested(fieldContext, value);
        }

        return value;
    }

}

