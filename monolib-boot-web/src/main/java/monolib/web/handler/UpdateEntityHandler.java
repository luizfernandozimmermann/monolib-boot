package monolib.web.handler;

import jakarta.validation.Valid;
import monolib.data.api.controller.EntityUpdateController;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.web.api.PutRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public abstract class UpdateEntityHandler<E extends EntityBase, D extends EntityDtoBase> {

    @Autowired
    protected EntityUpdateController<E, D> controller;

    @Transactional
    @PutRequest(path = "/{id}")
    public D update(UUID id, @Valid D input) {
        return controller.update(id, input);
    }

}
