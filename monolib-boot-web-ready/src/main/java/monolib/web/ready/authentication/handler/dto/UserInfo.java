package monolib.web.ready.authentication.handler.dto;

import lombok.Getter;
import lombok.Setter;
import monolib.core.model.User;

import java.util.UUID;

@Getter
@Setter
public class UserInfo {

    private UUID id;
    private String name;
    private String email;

    public UserInfo(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
    }
}
