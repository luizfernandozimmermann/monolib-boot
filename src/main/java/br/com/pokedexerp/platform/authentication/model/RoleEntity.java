package br.com.pokedexerp.platform.authentication.model;

import br.com.pokedexerp.platform.entity.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "admin")
    private boolean admin;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<RolePermissionEntity> permissions = new ArrayList<>();

}
