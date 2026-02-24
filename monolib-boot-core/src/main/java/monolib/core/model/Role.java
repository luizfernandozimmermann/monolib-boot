package monolib.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private UUID id;
    private String name;
    private boolean admin;

    public static Role of(String name) {
        return Role.builder().name(name).build();
    }

}
