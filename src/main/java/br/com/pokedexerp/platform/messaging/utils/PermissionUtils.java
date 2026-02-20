package br.com.pokedexerp.platform.messaging.utils;

import br.com.pokedexerp.platform.messaging.annotation.EntityHandler;
import br.com.pokedexerp.platform.entity.utils.ReflectionUtils;
import br.com.pokedexerp.platform.messaging.annotation.ApiRequest;
import br.com.pokedexerp.platform.messaging.annotation.Handler;
import lombok.experimental.UtilityClass;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.AnnotatedElement;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@UtilityClass
public class PermissionUtils {

    private static final Map<RequestMethod, String> ENTITY_OPERATION_MAP = Map.of(
            RequestMethod.GET, "read",
            RequestMethod.POST, "create",
            RequestMethod.PUT, "update",
            RequestMethod.DELETE, "delete"
    );

    public static String[] mergePermissions(AnnotatedElement target, ApiRequest apiRequest, Handler handlerRequest) {
        var apiRequestPermissions = apiRequest.permissions();
        var handlerRequestPermissions = handlerRequest.permissions();
        var permissions = new HashSet<>(List.of(apiRequestPermissions));
        permissions.addAll(List.of(handlerRequestPermissions));
        addOperationPermission(apiRequest, permissions);
        var entityPermission = buildEntityPermission(target, apiRequest);
        if (entityPermission != null) {
            permissions.add(entityPermission);
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

    public static String buildEntityPermission(AnnotatedElement target, ApiRequest apiRequest) {
        var entityHandler = ReflectionUtils.getAnnotation(target, EntityHandler.class);
        if (entityHandler != null) {
            var operation = getEntityOperation(apiRequest.method());
            return buildPermission(operation, entityHandler.path());
        }
        return null;
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
