package br.com.pokedexerp.platform.authentication.model;

import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.encrypt.converter.PasswordEncryptConverter;
import br.com.pokedexerp.platform.user.model.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "user_credential")
@EqualsAndHashCode(callSuper = true)
public class UserCredentialEntity extends BaseEntity {

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @Convert(converter = PasswordEncryptConverter.class)
    @Column(name = "password_hash")
    private String passwordHash;

}
