package monolib.web.entity.handler;

import jakarta.validation.Valid;
import monolib.data.base.controller.EntityCreateController;
import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import monolib.web.annotation.PostRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class CreateEntityHandler<E extends EntityBase, D extends EntityBaseDto> extends EntityCreateController<E, D> {

    @Override
    @PostRequest
    @Transactional
    public D create(@RequestBody @Valid D dto) {
        return super.create(dto);
    }

}
