package monolib.data.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import monolib.annotations.GenerateController;
import monolib.data.api.annotation.Field;
import monolib.data.api.model.LogicalEntityBase;

@Getter
@Setter
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@GenerateController
public class UserEntity extends LogicalEntityBase {

    @Field(minLength = 5, maxLength = 50)
    @Column(name = "name")
    private String name;

    @Field(updatable = false, minLength = 5, maxLength = 100)
    @Column(name = "email")
    private String email;

    @Field(updatable = false)
    @Column(name = "admin", updatable = false)
    private boolean admin;

}
