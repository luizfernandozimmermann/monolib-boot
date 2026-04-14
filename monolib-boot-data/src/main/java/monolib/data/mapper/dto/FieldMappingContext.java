    package monolib.data.mapper.dto;

    import com.querydsl.core.util.ReflectionUtils;
    import lombok.Getter;
    import lombok.SneakyThrows;
    import monolib.core.exception.BusinessException;
    import monolib.core.exception.ErrorCode;
    import monolib.data.api.mapper.EntityMetadata;

    import java.lang.reflect.Field;
    import java.util.Collection;

    public class FieldMappingContext {
        private final Field sourceField;
        private final MappingConfig<?, ?, ?> config;
        private final EntityMetadata metadata;

        @Getter
        private final Field targetField;
        @Getter
        private final Object sourceValue;

        public static FieldMappingContext of(Field sourceField, MappingConfig<?, ?, ?> config, EntityMetadata metadata) {
            return new FieldMappingContext(sourceField, config, metadata);
        }

        @SneakyThrows
        public FieldMappingContext(Field sourceField, MappingConfig<?, ?, ?> config, EntityMetadata metadata) {
            this.sourceField = sourceField;
            this.config = config;
            this.metadata = metadata;
            targetField = ReflectionUtils.getFieldOrNull(config.getTarget().getClass(), getSourceFieldName());
            sourceValue = getValueUsingGetter(config.getSource(), sourceField);
        }

        private Object getValueUsingGetter(Object source, Field field) {
            try {
                var getterName = "get" + capitalize(field.getName());
                var method = source.getClass().getMethod(getterName);
                return method.invoke(source);
            } catch (NoSuchMethodException _) {
                try {
                    field.setAccessible(true);
                    return field.get(source);
                } catch (IllegalAccessException ex) {
                    throw new BusinessException(ErrorCode.UNKNOWN, ex);
                }
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.UNKNOWN, e);
            }
        }

        private String capitalize(String name) {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        public String getSourceFieldName() {
            return sourceField.getName();
        }

        public boolean shouldMap() {
            return config.shouldMap(getSourceFieldName());
        }

        public boolean isAccessible() {
            return metadata.isAccessible(getSourceFieldName());
        }

        public boolean isIncludeNonUpdatable() {
            return config.isIncludeNonUpdatable();
        }

        public boolean isUpdatable() {
            return metadata.isUpdatable(getSourceFieldName());
        }

        public boolean isTargetMap() {
            return config.isTargetMap();
        }

        public void setMapField(Object map) {
            config.setMapField(getSourceFieldName(), map);
        }

        @SneakyThrows
        public void setTargetFieldValue(Object value) {
            var field = getTargetField();
            field.setAccessible(true);
            field.set(config.getTarget(), value);
        }

        public boolean isMaxDepthReached() {
            return config.getContext().isMaxDepthReached();
        }

        public boolean isAlreadyMapped(Object value) {
            return config.getContext().isAlreadyMapped(value);
        }

        public Object getMapped(Object value) {
            return config.getContext().getMapped(value);
        }

        public void increaseDepth() {
            config.getContext().increaseDepth();
        }

        public Collection<String> childProjection() {
            return config.childProjection(getSourceFieldName());
        }

        public Object callNestedMappingFunction(Object source, Object target) {
            return config.getNestedMappingFunction().mapObject(source, target, config.getContext());
        }

        public void register(Object value, Object result) {
            config.getContext().register(value, result);
        }

        public void decreaseDepth() {
            config.getContext().decreaseDepth();
        }
    }
