package monolib.data.domain.userrole.model;

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
import monolib.data.domain.role.model.RoleEntity;
import monolib.data.domain.user.model.UserEntity;

@Getter
@Setter
@Entity
@Table(name = "user_role")
@EqualsAndHashCode(callSuper = true)
public class UserRoleEntity extends EntityBase {

    @Field(updatable = false)
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @Field(updatable = false)
    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private RoleEntity role;

}
