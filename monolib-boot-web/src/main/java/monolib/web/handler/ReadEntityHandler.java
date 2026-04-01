package monolib.web.handler;

import jakarta.validation.Valid;
import monolib.data.api.controller.EntityReadController;
import monolib.data.api.controller.model.EntityPageable;
import monolib.data.api.model.EntityBase;
import monolib.data.api.model.EntityDtoBase;
import monolib.web.api.GetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;
import java.util.UUID;

@Component
public abstract class ReadEntityHandler<E extends EntityBase, D extends EntityDtoBase> {

    @Autowired
    protected EntityReadController<E, D> controller;

    @GetRequest
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> list(@ModelAttribute @Valid EntityPageable input) {
        return controller.list(input);
    }

    @GetRequest(path = "/{id}")
    @Transactional(readOnly = true)
    public D get(UUID id) {
        return controller.get(id);
    }

}
