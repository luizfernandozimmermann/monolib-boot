package monolib.data.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Field
    @Column(name = "name")
    @NotNull
    @Size(min = 5, max = 50)
    private String name;

    @Field(updatable = false)
    @Column(name = "email")
    @NotNull
    @Size(min = 5, max = 100)
    private String email;

    @Field(updatable = false)
    @Column(name = "admin", updatable = false)
    private boolean admin;

}
