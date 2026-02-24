package monolib.data.base.controller;

import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.translation.TranslationService;
import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import monolib.data.base.repository.EntityBaseRepository;
import monolib.data.mapper.AutoMapper;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.UUID;

@Component
public abstract class EntityControllerBase<E extends EntityBase, D extends EntityBaseDto> {

    protected Class<E> entityClass = getGenericClass(0);

    protected Class<D> dtoClass = getGenericClass(1);

    private String entityNotFoundMessage;

    @Autowired
    protected EntityBaseRepository<E> repository;

    @Autowired
    protected TranslationService translationService;

    @Autowired
    protected AutoMapper mapper;

    @SuppressWarnings("unchecked")
    private <Y> Class<Y> getGenericClass(int index) {
        return (Class<Y>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index];
    }

    protected E findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, getEntityNotFoundMessage()));
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
