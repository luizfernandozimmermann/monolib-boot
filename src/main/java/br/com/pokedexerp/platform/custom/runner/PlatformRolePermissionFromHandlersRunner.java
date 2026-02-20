package br.com.pokedexerp.platform.custom.runner;

import br.com.pokedexerp.platform.authentication.service.PermissionService;
import br.com.pokedexerp.platform.custom.runner.builder.PermissionTreeBuilder;
import br.com.pokedexerp.platform.messaging.annotation.ApiRequest;
import br.com.pokedexerp.platform.messaging.annotation.Handler;
import br.com.pokedexerp.platform.messaging.utils.PermissionCache;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlatformRolePermissionFromHandlersRunner {

    PermissionService permissionService;

    ApplicationContext context;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        var permissions = PermissionTreeBuilder.build(new ArrayList<>(getPermissions()));
        permissionService.create(permissions);
    }

    private Set<String> getPermissions() {
        var permissions = new LinkedHashSet<String>();
        var beans = context.getBeansWithAnnotation(Handler.class).values();
        beans.forEach(bean -> addPermissionsFromBean(bean, permissions));
        return permissions;
    }

    private static void addPermissionsFromBean(Object bean, LinkedHashSet<String> permissions) {
        var targetClass = AopUtils.getTargetClass(bean);
        var handler = AnnotatedElementUtils.findMergedAnnotation(targetClass, Handler.class);
        if (handler == null) {
            return;
        }
        addPermissionsFromMethods(targetClass, permissions, handler);
    }

    private static void addPermissionsFromMethods(Class<?> targetClass, LinkedHashSet<String> permissions, Handler handler) {
        ReflectionUtils.doWithMethods(targetClass, method -> {
            var apiRequest = AnnotatedElementUtils.findMergedAnnotation(method, ApiRequest.class);
            if (apiRequest == null) {
                return;
            }
            permissions.addAll(List.of(PermissionCache.get(targetClass, method, apiRequest, handler)));
        });
    }

}
