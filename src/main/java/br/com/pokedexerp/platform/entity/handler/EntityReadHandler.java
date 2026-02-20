package br.com.pokedexerp.platform.entity.handler;

import br.com.pokedexerp.platform.entity.handler.dto.ListEntityInput;
import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import br.com.pokedexerp.platform.entity.specification.factory.FilterSpecificationFactory;
import br.com.pokedexerp.platform.messaging.annotation.GetRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;
import java.util.UUID;

@Component
public abstract class EntityReadHandler<E extends BaseEntity, D extends BaseEntityDto> extends BaseEntityHandler<E, D> {

    @Autowired
    private FilterSpecificationFactory specificationFactory;

    @GetRequest
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> list(@ModelAttribute @Valid ListEntityInput input) {
        var pageable = Pageable.ofSize(input.getSize()).withPage(input.getPage());
        return repository.findAll(specificationFactory.create(input.getFilter(), input.getOrderBy()), pageable)
                .map(e -> mapper.entityToDto(e, input.getFields()));
    }

    @GetRequest("/{id}")
    @Transactional(readOnly = true)
    public D get(UUID id) {
        return mapper.entityToDto(findById(id), dtoClass);
    }

}
