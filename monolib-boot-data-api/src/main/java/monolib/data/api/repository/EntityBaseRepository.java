package monolib.data.api.repository;

import monolib.data.api.model.EntityBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EntityBaseRepository<T extends EntityBase> extends JpaRepository<T, UUID> {
    Page<T> findAll(Specification<T> build, Pageable pageable);
}
