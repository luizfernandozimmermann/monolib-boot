package monolib.web.handler;

import jakarta.validation.Valid;
import monolib.data.api.controller.EntityCreateController;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.web.api.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public abstract class CreateEntityHandler<E extends EntityBase, D extends EntityDtoBase> {

    @Autowired
    protected EntityCreateController<E, D> controller;

    @PostRequest
    @Transactional
    public D create(@RequestBody @Valid D dto) {
        return controller.create(dto);
    }

}
