package br.com.pokedexerp.platform.user.model;

import br.com.pokedexerp.platform.entity.annotation.Field;
import br.com.pokedexerp.platform.entity.model.LogicalBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends LogicalBaseEntity {

    @Field
    @Column(name = "name")
    private String name;

    @Field(updatable = false)
    @Column(name = "email")
    private String email;

    @Field(accessible = false, updatable = false)
    @Column(name = "admin", updatable = false)
    private boolean admin;

}
