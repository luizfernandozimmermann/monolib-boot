package monolib.data.api.mapper;

import monolib.data.api.model.EntityBase;
import com.querydsl.core.util.ReflectionUtils;
import jakarta.persistence.Id;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EntityMetadata {

    private static final Map<Class<?>, EntityMetadata> CACHE = new ConcurrentHashMap<>();

    private final Map<String, Field> fields = new HashMap<>();
    private final Set<String> nonUpdatableFields = new HashSet<>();
    private final Set<String> nonAccessibleFields = new HashSet<>();
    @Getter
    private String idField;
    @Getter
    private String softDeleteField;

    private EntityMetadata(Class<?> clazz) {
        for (var field : ReflectionUtils.getFields(clazz)) {
            field.setAccessible(true);
            fields.put(field.getName(), field);

            var isId = field.isAnnotationPresent(Id.class);
            if (isId) {
                idField = field.getName();
            }
            processEntityFieldAnnotation(field, isId);
        }
    }

    private void processEntityFieldAnnotation(Field field, boolean isId) {
        var fieldAnnotation = field.getAnnotation(monolib.data.api.annotation.Field.class);
        if (fieldAnnotation != null) {
            if (fieldAnnotation.softDelete()) {
                softDeleteField = field.getName();
            }
            if (!fieldAnnotation.updatable()) {
                nonUpdatableFields.add(field.getName());
            }
            if (!fieldAnnotation.accessible()) {
                nonAccessibleFields.add(field.getName());
                nonUpdatableFields.add(field.getName());
            }
        } else {
            nonUpdatableFields.add(field.getName());
            if (!isId) {
                nonAccessibleFields.add(field.getName());
            }
        }
    }

    public static EntityMetadata of(Class<? extends EntityBase> clazz) {
        return CACHE.computeIfAbsent(clazz, EntityMetadata::new);
    }

    public Field getField(String name) {
        return fields.get(name);
    }

    public boolean isUpdatable(String field) {
        return !nonUpdatableFields.contains(field);
    }

    public boolean isAccessible(String field) {
        return !nonAccessibleFields.contains(field);
    }

}
