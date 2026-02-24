package monolib.data.base.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class LogicalEntityBaseDto extends EntityBaseDto {

    protected boolean active;

    protected LogicalEntityBaseDto(LogicalEntityBase entity) {
        super(entity);
        this.active = entity.isActive();
    }

}
