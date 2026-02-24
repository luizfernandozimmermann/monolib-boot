package monolib.data.domain.userrole.repository;

import java.util.UUID;

public interface UserRoleCustomRepository {
    boolean userHasPermission(UUID userId, String[] permission);
}
