package monolib.data.domain.usercredential.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import monolib.annotations.GenerateRepository;
import monolib.data.api.annotation.Field;
import monolib.data.api.model.EntityBase;
import monolib.data.domain.user.model.UserEntity;

@Getter
@Setter
@Entity
@Table(name = "user_credential")
@EqualsAndHashCode(callSuper = true)
@GenerateRepository
public class UserCredentialEntity extends EntityBase {

    @Field(updatable = false, accessible = false)
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @Field(accessible = false)
    @Column(name = "password_hash")
    private String passwordHash;

}
