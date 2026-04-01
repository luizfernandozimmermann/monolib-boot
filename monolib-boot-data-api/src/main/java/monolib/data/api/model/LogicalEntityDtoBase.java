package monolib.data.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class LogicalEntityDtoBase extends EntityDtoBase {

    protected boolean active;

    protected LogicalEntityDtoBase(LogicalEntityBase entity) {
        super(entity);
        this.active = entity.isActive();
    }

}
