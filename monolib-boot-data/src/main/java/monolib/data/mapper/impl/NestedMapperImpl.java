package monolib.data.mapper.impl;

import monolib.data.mapper.NestedMapper;
import monolib.data.mapper.dto.MappingConfig;
import monolib.data.base.model.EntityBase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NestedMapperImpl implements NestedMapper {

    @Override
    public Object mapNested(Field sourceField, Object value, MappingConfig<?, ?, ?> config) {
        var context = config.getContext();
        if (context.isMaxDepthReached()) {
            return null;
        }
        if (context.isAlreadyMapped(value)) {
            return context.getMapped(value);
        }
        context.increaseDepth();
        return mapNestedInternal(sourceField, value, config);
    }

    private Object mapNestedInternal(Field sourceField, Object value, MappingConfig<?, ?, ?> config) {
        var context = config.getContext();
        try {
            var nestedFields = config.childProjection(sourceField.getName());
            var result = config.getNestedMappingFunction().map((EntityBase) value, nestedFields);
            context.register(value, result);
            return result;
        } finally {
            context.decreaseDepth();
        }
    }

}
