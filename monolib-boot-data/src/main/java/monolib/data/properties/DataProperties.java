package monolib.data.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "monolib.data")
public class DataProperties {

    private String url;
    private String user;
    private String password;
    private String driver;

}
