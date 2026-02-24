package monolib.data.domain.user.model;

import monolib.data.base.model.LogicalEntityBaseDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends LogicalEntityBaseDto {

    private String name;

    private String email;

    public UserDto(UserEntity user) {
        super(user);
        name = user.getName();
        email = user.getEmail();
    }

}
