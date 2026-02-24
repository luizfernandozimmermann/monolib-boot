package monolib.data.base.model;

import monolib.data.annotation.Field;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Filter(name = "activeFilter", condition = "active = :isActive")
@FilterDef(name = "activeFilter", parameters = @ParamDef(name = "isActive", type = Boolean.class))
public abstract class LogicalEntityBase extends EntityBase {

    @Field(softDelete = true)
    @Column(name = "active")
    protected boolean active = true;

}
