package br.com.pokedexerp.platform.entity.handler;

import br.com.pokedexerp.platform.entity.mapper.AutoMapper;
import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import br.com.pokedexerp.platform.entity.repository.BaseEntityRepository;
import br.com.pokedexerp.platform.entity.utils.ReflectionUtils;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public abstract class BaseEntityHandler<E extends BaseEntity, D extends BaseEntityDto> {

    protected Class<E> entityClass = ReflectionUtils.getGenericClass(getClass(), 0);

    protected Class<D> dtoClass = ReflectionUtils.getGenericClass(getClass(), 1);

    private String entityNotFoundMessage;

    @Autowired
    protected BaseEntityRepository<E> repository;

    @Autowired
    protected TranslationService translationService;

    @Autowired
    protected AutoMapper mapper;

    protected E findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, getEntityNotFoundMessage()));
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
