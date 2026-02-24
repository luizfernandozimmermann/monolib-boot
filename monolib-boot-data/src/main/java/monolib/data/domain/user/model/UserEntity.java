package monolib.data.domain.user.model;

import monolib.data.annotation.Field;
import monolib.data.base.model.LogicalEntityBase;
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
public class UserEntity extends LogicalEntityBase {

    @Field
    @Column(name = "name")
    private String name;

    @Field(updatable = false)
    @Column(name = "email")
    private String email;

    @Field(updatable = false)
    @Column(name = "admin", updatable = false)
    private boolean admin;

}
