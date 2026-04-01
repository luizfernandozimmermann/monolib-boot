package monolib.data.domain.rolepermission.model;

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
import monolib.data.domain.permission.model.PermissionEntity;
import monolib.data.domain.role.model.RoleEntity;

@Getter
@Setter
@Entity
@Table(name = "role_permission")
@EqualsAndHashCode(callSuper = true)
@GenerateRepository
public class RolePermissionEntity extends EntityBase {

    @Field(updatable = false)
    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private RoleEntity role;

    @Field(updatable = false)
    @JoinColumn(name = "permission_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PermissionEntity permission;

}
