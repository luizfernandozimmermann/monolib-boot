package monolib.infra.runner.dto;

import lombok.Getter;
import lombok.Setter;
import monolib.core.model.Permission;

import java.util.List;

@Getter
@Setter
public class RolePermissionSeed {

    private List<String> roles;
    private List<Permission> permissions;

}
