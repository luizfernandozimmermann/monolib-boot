package monolib.core.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "resource")
public class Permission {

    private UUID id;

    private String resource;

    private String description;

    private Permission parent;

    private List<Permission> children = new ArrayList<>();

    public Permission(String resource, String description, Permission parent) {
        this.resource = resource;
        this.description = description;
        this.parent = parent;
    }

    public void addChild(Permission node) {
        this.children.add(node);
    }
}
