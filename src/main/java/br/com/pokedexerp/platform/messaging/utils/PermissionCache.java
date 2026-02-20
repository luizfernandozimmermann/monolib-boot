package br.com.pokedexerp.platform.messaging.utils;

import br.com.pokedexerp.platform.messaging.annotation.ApiRequest;
import br.com.pokedexerp.platform.messaging.annotation.Handler;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class PermissionCache {

    private static final Map<String, String[]> CACHE = new HashMap<>();

    public static String[] get(Method method, ApiRequest apiRequest, Handler handler) {
        return get(method.getDeclaringClass(), method, apiRequest, handler);
    }

    public static String[] get(Class<?> clazz, Method method, ApiRequest apiRequest, Handler handler) {
        return CACHE.computeIfAbsent(getCacheKey(clazz, method), _ -> PermissionUtils.mergePermissions(clazz, apiRequest, handler));
    }

    private static String getCacheKey(Class<?> clazz, Method method) {
        return clazz.getSimpleName() + "." + method.getName();
    }

}
