package monolib.web.runner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.service.PermissionCoreService;
import monolib.core.runner.ApplicationStartedRunner;
import monolib.web.api.ApiRequest;
import monolib.web.api.Handler;
import monolib.web.permission.WebPermissionCache;
import monolib.web.runner.builder.PermissionTreeBuilder;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
public class MonolibRolePermissionFromHandlersRunner implements ApplicationStartedRunner {

    PermissionCoreService permissionService;

    ApplicationContext context;

    @Override
    @Transactional
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
            permissions.addAll(List.of(WebPermissionCache.get(targetClass, method, apiRequest, handler)));
        });
    }

}
