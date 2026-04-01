package monolib.data.domain.role.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import monolib.annotations.GenerateCRUDService;
import monolib.data.api.annotation.Field;
import monolib.data.api.model.EntityBase;
import monolib.data.domain.rolepermission.model.RolePermissionEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
@GenerateCRUDService
public class RoleEntity extends EntityBase {

    @Column(name = "name")
    @Field(updatable = false)
    private String name;

    @Column(name = "admin")
    @Field(updatable = false)
    private boolean admin;

    @Field(updatable = false)
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<RolePermissionEntity> permissions = new ArrayList<>();

}
