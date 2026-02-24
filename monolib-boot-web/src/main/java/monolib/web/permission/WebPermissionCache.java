package monolib.web.permission;

import lombok.experimental.UtilityClass;
import monolib.web.annotation.ApiRequest;
import monolib.web.annotation.Handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class WebPermissionCache {

    private static final Map<String, String[]> CACHE = new HashMap<>();

    public static String[] get(Method method, ApiRequest apiRequest, Handler handler) {
        return get(method.getDeclaringClass(), method, apiRequest, handler);
    }

    public static String[] get(Class<?> clazz, Method method, ApiRequest apiRequest, Handler handler) {
        return CACHE.computeIfAbsent(getCacheKey(clazz, method), _ -> PermissionProvider.mergePermissions(apiRequest, handler));
    }

    private static String getCacheKey(Class<?> clazz, Method method) {
        return clazz.getSimpleName() + "." + method.getName();
    }

}
