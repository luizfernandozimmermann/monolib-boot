package monolib.data.mapper.impl;

import com.querydsl.core.util.ReflectionUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import monolib.data.mapper.FieldMapper;
import monolib.data.mapper.ValueMapper;
import monolib.data.mapper.dto.MappingConfig;
import monolib.data.api.mapper.EntityMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
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
                .forEach(field -> processField(field, config, metadata));
    }

    @SneakyThrows
    private void processField(Field sourceField, MappingConfig<?, ?, ?> config, EntityMetadata metadata) {
        var name = sourceField.getName();
        if (!config.shouldMap(name) || !metadata.isAccessible(name)) {
            return;
        }
        if (!config.isIncludeNonUpdatable() && !metadata.isUpdatable(name)) {
            return;
        }
        processField(sourceField, config);
    }

    @SneakyThrows
    private void processField(Field sourceField, MappingConfig<?, ?, ?> config) {
        sourceField.setAccessible(true);
        var value = sourceField.get(config.getSource());
        if (Objects.isNull(value)) {
            return;
        }
        if (config.isTargetMap()) {
            mapMapField(sourceField, value, config);
            return;
        }
        mapObjectField(sourceField, value, config);
    }

    @SneakyThrows
    private void mapObjectField(Field sourceField, Object value, MappingConfig<?, ?, ?> config) {
        var targetField = ReflectionUtils.getFieldOrNull(config.getTarget().getClass(), sourceField.getName());
        if (Objects.isNull(targetField)) {
            return;
        }
        var mapped = valueMapper.map(sourceField, value, config);
        targetField.setAccessible(true);
        targetField.set(config.getTarget(), mapped);
    }

    @SneakyThrows
    private void mapMapField(Field sourceField, Object value, MappingConfig<?, ?, ?> config) {
        config.setMapField(sourceField.getName(), valueMapper.map(sourceField, value, config));
    }

}
