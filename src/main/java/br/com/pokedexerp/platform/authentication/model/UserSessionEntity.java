package br.com.pokedexerp.platform.authentication.model;

import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.encrypt.converter.EncryptConverter;
import br.com.pokedexerp.platform.user.model.UserEntity;
import jakarta.annotation.Nullable;
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

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_session")
@EqualsAndHashCode(callSuper = true)
public class UserSessionEntity extends BaseEntity {

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "access_token_hash")
    private String accessTokenHash;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "refresh_token_hash")
    private String refreshTokenHash;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Nullable
    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    @Column(name = "first_session")
    private boolean firstSession;

}
