package monolib.web.permission;

import lombok.experimental.UtilityClass;
import monolib.web.api.ApiRequest;
import monolib.web.api.Handler;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@UtilityClass
public class PermissionProvider {

    private static final Map<RequestMethod, String> ENTITY_OPERATION_MAP = Map.of(
            RequestMethod.GET, "read",
            RequestMethod.POST, "create",
            RequestMethod.PUT, "update",
            RequestMethod.DELETE, "delete"
    );

    public static String[] mergePermissions(ApiRequest apiRequest, Handler handlerRequest) {
        var apiRequestPermissions = apiRequest.permissions();
        var handlerRequestPermissions = handlerRequest.permissions();
        var permissions = new HashSet<>(List.of(apiRequestPermissions));
        permissions.addAll(List.of(handlerRequestPermissions));
        addOperationPermission(apiRequest, permissions);
        if (handlerRequest.type() == Handler.ControllerType.ENTITY) {
            permissions.add(buildEntityPermission(apiRequest, handlerRequest));
        }
        return permissions.toArray(new String[0]);
    }

    private static void addOperationPermission(ApiRequest apiRequest, HashSet<String> permissions) {
        permissions.forEach(permission -> {
            if (!permission.contains(":")) {
                var operation = getOperation(apiRequest.method());
                permissions.remove(permission);
                permissions.add(buildPermission(operation, permission));
            }
        });
    }

    private static String getOperation(RequestMethod method) {
        if (method == RequestMethod.GET) {
            return "read";
        }
        return "process";
    }

    public static String buildEntityPermission(ApiRequest apiRequest, Handler handlerRequest) {
        var operation = getEntityOperation(apiRequest.method());
        return buildPermission(operation, handlerRequest.path());
    }

    private static String buildPermission(String operation, String... path) {
        return replaceSlash(path) + ":" + operation;
    }

    private static String replaceSlash(String... path) {
        return path[0].replace("/", ".");
    }

    private static String getEntityOperation(RequestMethod method) {
        return ENTITY_OPERATION_MAP.get(method);
    }
}
