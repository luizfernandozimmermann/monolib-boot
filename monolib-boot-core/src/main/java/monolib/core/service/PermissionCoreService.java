package monolib.core.service;

import monolib.core.model.Permission;

import java.util.List;

public interface PermissionCoreService {
    void create(List<Permission> permissions);
}
