package monolib.web.handler;

import monolib.data.api.controller.EntityDeleteController;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.web.api.DeleteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public abstract class DeleteEntityHandler<E extends EntityBase, D extends EntityDtoBase> {

    @Autowired
    protected EntityDeleteController<E, D> controller;

    @Transactional
    @DeleteRequest(path = "/{id}")
    public void delete(UUID id) {
        controller.delete(id);
    }

}
