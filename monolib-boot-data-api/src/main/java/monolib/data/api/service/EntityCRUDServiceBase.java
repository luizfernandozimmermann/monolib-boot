package monolib.data.api.service;

import monolib.data.api.model.EntityBase;
import monolib.data.api.repository.EntityBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public abstract class EntityCRUDServiceBase<T extends EntityBase> {

    @Autowired
    protected EntityBaseRepository<T> baseRepository;

    @Transactional(readOnly = true)
    public Page<T> findAll(Specification<T> build, Pageable pageable) {
        return baseRepository.findAll(build, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<T> findById(UUID id) {
        return baseRepository.findById(id);
    }

    @Transactional
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Transactional
    public void delete(T entity) {
        baseRepository.delete(entity);
    }

}
