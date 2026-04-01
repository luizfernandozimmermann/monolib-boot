package monolib.data.api.controller;

import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.translation.TranslationService;
import monolib.data.api.mapper.AutoMapper;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.data.api.repository.EntityBaseRepository;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.UUID;

@Component
public abstract class EntityControllerBase<E extends EntityBase, D extends EntityDtoBase> {

    protected Class<E> entityClass = getGenericClass(0);

    protected Class<D> dtoClass = getGenericClass(1);

    private String entityNotFoundMessage;

    @Autowired
    protected TranslationService translationService;

    @Autowired
    protected AutoMapper mapper;

    private ParameterizedType parameterizedType;
    @SuppressWarnings("unchecked")
    private <Y> Class<Y> getGenericClass(int index) {
        if (Objects.nonNull(parameterizedType)) {
            return (Class<Y>) parameterizedType.getActualTypeArguments()[index];
        }
        Class<?> actualClass = getClass();
        while (Objects.nonNull(actualClass)) {
            var generic = actualClass.getGenericSuperclass();

            if (generic instanceof ParameterizedType pt) {
                parameterizedType = pt;
                return (Class<Y>) parameterizedType.getActualTypeArguments()[index];
            }

            actualClass = actualClass.getSuperclass();
        }
        throw new IllegalStateException("Could not found generic class");
    }

    protected abstract EntityBaseRepository<E> getRepository();

    protected E findById(UUID id) {
        return getRepository().findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, getEntityNotFoundMessage()));
    }

    private String getEntityNotFoundMessage() {
        if (entityNotFoundMessage == null) {
            var entityName = entityClass.getSimpleName().replace("Entity", "");
            var entityNotFoundKey = "entity.%s.not_found".formatted(CaseUtils.toCamelCase(entityName, false));
            entityNotFoundMessage = translationService.getMessage(entityNotFoundKey);
        }
        return entityNotFoundMessage;
    }

}
