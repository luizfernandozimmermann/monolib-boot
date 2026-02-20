package br.com.pokedexerp.platform.entity.repository;

import br.com.pokedexerp.platform.entity.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, UUID> {
    Page<T> findAll(Specification<T> build, Pageable pageable);
}
