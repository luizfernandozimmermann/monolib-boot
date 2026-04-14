package monolib.data.mapper.impl;

import com.querydsl.core.util.ReflectionUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import monolib.data.api.mapper.EntityMetadata;
import monolib.data.mapper.FieldMapper;
import monolib.data.mapper.ValueMapper;
import monolib.data.mapper.dto.FieldMappingContext;
import monolib.data.mapper.dto.MappingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FieldMapperImpl implements FieldMapper {

    ValueMapper valueMapper;

    @Override
    public void mapFields(MappingConfig<?, ?, ?> config) {
        var metadata = EntityMetadata.of(config.getEntityClass());
        ReflectionUtils.getFields(config.getSource().getClass())
                .forEach(field -> processField(FieldMappingContext.of(field, config, metadata)));
    }

    @SneakyThrows
    private void processField(FieldMappingContext fieldContext) {
        if (shouldNotProcess(fieldContext)) {
            return;
        }
        if (fieldContext.isTargetMap()) {
            mapMapField(fieldContext);
            return;
        }
        mapObjectField(fieldContext);
    }

    private static boolean shouldNotProcess(FieldMappingContext fieldContext) {
        return !fieldContext.shouldMap() || !fieldContext.isAccessible() ||
                (!fieldContext.isIncludeNonUpdatable() && !fieldContext.isUpdatable());
    }

    @SneakyThrows
    private void mapObjectField(FieldMappingContext fieldContext) {
        var targetField = fieldContext.getTargetField();
        if (Objects.isNull(targetField)) {
            return;
        }
        var mapped = valueMapper.map(fieldContext);
        fieldContext.setTargetFieldValue(mapped);
    }

    @SneakyThrows
    private void mapMapField(FieldMappingContext fieldContext) {
        fieldContext.setMapField(valueMapper.map(fieldContext));
    }

}
