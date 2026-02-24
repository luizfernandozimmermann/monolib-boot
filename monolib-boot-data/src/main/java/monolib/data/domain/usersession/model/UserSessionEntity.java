package monolib.data.domain.usersession.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import monolib.data.annotation.Field;
import monolib.data.base.model.EntityBase;
import monolib.data.domain.user.model.UserEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_session")
@EqualsAndHashCode(callSuper = true)
public class UserSessionEntity extends EntityBase {

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @Field(updatable = false, accessible = false)
    private UserEntity user;

    @Column(name = "access_token_hash")
    @Field(updatable = false, accessible = false)
    private String accessTokenHash;

    @Column(name = "refresh_token_hash")
    @Field(updatable = false, accessible = false)
    private String refreshTokenHash;

    @Column(name = "expires_at")
    @Field(updatable = false, accessible = false)
    private LocalDateTime expiresAt;

    @Nullable
    @Column(name = "revoked_at")
    @Field(updatable = false, accessible = false)
    private LocalDateTime revokedAt;

    @Column(name = "first_session")
    @Field(updatable = false, accessible = false)
    private boolean firstSession;

}
