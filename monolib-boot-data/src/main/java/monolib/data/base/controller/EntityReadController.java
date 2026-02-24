package monolib.data.base.controller;

import jakarta.validation.Valid;
import monolib.data.base.controller.model.EntityPageable;
import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import monolib.data.specification.factory.FilterSpecificationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Component
public abstract class EntityReadController<E extends EntityBase, D extends EntityBaseDto> extends EntityControllerBase<E, D> {

    @Autowired
    private FilterSpecificationFactory specificationFactory;

    @Transactional(readOnly = true)
    public Page<Map<String, Object>> list(@Valid EntityPageable input) {
        var pageable = Pageable.ofSize(input.getSize()).withPage(input.getPage());
        return repository.findAll(specificationFactory.create(input.getFilter(), input.getOrderBy()), pageable)
                .map(e -> mapper.entityToDto(e, input.getFields()));
    }

    @Transactional(readOnly = true)
    public D get(UUID id) {
        return mapper.entityToDto(findById(id), dtoClass);
    }

}
