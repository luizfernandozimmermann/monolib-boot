package monolib.web.ready.authentication.handler.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInInput {

    @NotNull @Valid
    private LoginInfo login;

}
