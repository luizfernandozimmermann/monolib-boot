package monolib.data.domain.permission.model;

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

@Getter
@Setter
@Entity
@Table(name = "permission")
@EqualsAndHashCode(callSuper = true)
public class PermissionEntity extends EntityBase {

    @Field(updatable = false)
    @JoinColumn(name = "parent_permission_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PermissionEntity parentPermission;

    @Field(updatable = false)
    @Column(name = "resource", length = 100)
    private String resource;

    @Field(updatable = false)
    @Column(name = "description")
    private String description;

}
