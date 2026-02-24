package monolib.web.entity.handler;

import jakarta.validation.Valid;
import monolib.data.base.controller.EntityUpdateController;
import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import monolib.web.annotation.PutRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public abstract class UpdateEntityHandler<E extends EntityBase, D extends EntityBaseDto> extends EntityUpdateController<E, D> {

    @Override
    @Transactional
    @PutRequest(path = "/{id}")
    public D update(UUID id, @Valid D input) {
        return super.update(id, input);
    }

}
