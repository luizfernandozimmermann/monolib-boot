package monolib.data.mapper.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.data.mapper.NestedMapper;
import monolib.data.mapper.dto.FieldMappingContext;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class NestedMapperImpl implements NestedMapper {

    @Autowired
    private ApplicationContext applicationContext;
    private Repositories repositories;

    @PostConstruct
    void postConstruct() {
        this.repositories = new Repositories(applicationContext);
    }

    @Override
    public Object mapNested(FieldMappingContext fieldContext, Object value) {
        if (fieldContext.isMaxDepthReached()) {
            return null;
        }
        if (fieldContext.isAlreadyMapped(value)) {
            return fieldContext.getMapped(value);
        }
        fieldContext.increaseDepth();
        return mapNestedInternal(fieldContext, value);
    }

    private Object mapNestedInternal(FieldMappingContext fieldContext, Object value) {
        var targetField = fieldContext.getTargetField();
        if (targetField != null && EntityBase.class.isAssignableFrom(targetField.getType())) {
            return mapNestedEntity(fieldContext, (EntityDtoBase) value);
        }
        return mapNestedFunction(fieldContext, value);
    }

    private Object mapNestedEntity(FieldMappingContext fieldContext, EntityDtoBase value) {
        try {
            var targetField = fieldContext.getTargetField();
            var id = value.getId();
            if (id == null) {
                return value;
            }
            var repository = resolveRepository(targetField.getType());
            var managedEntity = repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Entity not found for id " + id));
            if (hasCascadeUpdate(targetField)) {
                mapNestedFunction(fieldContext, managedEntity);
            }
            fieldContext.register(value, managedEntity);
            return managedEntity;
        } finally {
            fieldContext.decreaseDepth();
        }
    }

    @SuppressWarnings("unchecked")
    private JpaRepository<Object, Object> resolveRepository(Class<?> entityType) {
        return (JpaRepository<Object, Object>) repositories.getRepositoryFor(entityType).orElseThrow(() ->
                new BusinessException(ErrorCode.UNKNOWN, "Repository not found for " + entityType.getName()));
    }

    private Object mapNestedFunction(FieldMappingContext fieldContext, Object value) {
        try {
            var nestedFields = fieldContext.childProjection();
            var result = fieldContext.callNestedMappingFunction(value, nestedFields);
            fieldContext.register(value, result);
            return result;
        } finally {
            fieldContext.decreaseDepth();
        }
    }

    public static boolean hasCascadeUpdate(Field field) {
        if (field == null) {
            return false;
        }

        return checkCascade(field.getAnnotation(OneToMany.class)) ||
                checkCascade(field.getAnnotation(ManyToMany.class)) ||
                checkCascade(field.getAnnotation(ManyToOne.class)) ||
                checkCascade(field.getAnnotation(OneToOne.class));
    }

    private static boolean checkCascade(Object annotation) {
        var cascades = getCascadeTypes(annotation);
        if (cascades == null) {
            return false;
        }
        return Arrays.stream(cascades)
                .anyMatch(c -> c == CascadeType.ALL || c == CascadeType.MERGE);
    }

    private static CascadeType @Nullable [] getCascadeTypes(Object annotation) {
        if (annotation == null) {
            return null;
        }
        return switch (annotation) {
            case OneToMany a -> a.cascade();
            case ManyToMany a -> a.cascade();
            case ManyToOne a -> a.cascade();
            case OneToOne a -> a.cascade();
            default -> null;
        };
    }

}
