package monolib.web.entity.handler;

import jakarta.validation.Valid;
import monolib.data.base.controller.EntityReadController;
import monolib.data.base.controller.model.EntityPageable;
import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import monolib.web.annotation.GetRequest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;
import java.util.UUID;

public abstract class ReadEntityHandler<E extends EntityBase, D extends EntityBaseDto> extends EntityReadController<E, D> {

    @Override
    @GetRequest
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> list(@ModelAttribute @Valid EntityPageable input) {
        return super.list(input);
    }

    @Override
    @GetRequest(path = "/{id}")
    @Transactional(readOnly = true)
    public D get(UUID id) {
        return super.get(id);
    }

}
