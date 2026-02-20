package br.com.pokedexerp.platform.authentication.model;

import br.com.pokedexerp.platform.entity.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission")
@EqualsAndHashCode(callSuper = true)
public class PermissionEntity extends BaseEntity {

    @JoinColumn(name = "parent_permission_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PermissionEntity parentPermission;

    @Column(name = "resource", length = 100)
    private String resource;

    @Column(name = "description")
    private String description;

}
