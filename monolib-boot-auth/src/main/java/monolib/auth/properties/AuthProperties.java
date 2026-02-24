package monolib.auth.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "monolib.auth")
public class AuthProperties {

    private Long sessionTokenExpirationTime = 900L;

    @NotNull
    private String sessionToken;

    @NotNull
    private String passwordToken;

}
