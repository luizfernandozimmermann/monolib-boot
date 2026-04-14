package monolib.generator.data;

import lombok.Getter;

@Getter
public class ValidationParams {

    private Boolean notNull;
    private Integer minLength;
    private Integer maxLength;
    private String regexp;

}
