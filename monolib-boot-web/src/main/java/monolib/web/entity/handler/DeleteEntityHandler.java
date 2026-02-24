package monolib.web.entity.handler;

import monolib.data.base.controller.EntityDeleteController;
import monolib.data.base.model.EntityBase;
import monolib.data.base.model.EntityBaseDto;
import monolib.web.annotation.DeleteRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public abstract class DeleteEntityHandler<E extends EntityBase, D extends EntityBaseDto> extends EntityDeleteController<E, D> {

    @Override
    @Transactional
    @DeleteRequest(path = "/{id}")
    public void delete(UUID id) {
        super.delete(id);
    }

}
