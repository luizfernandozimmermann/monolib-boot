package monolib.data.utils;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ActiveFilterUtils {

    public static final String ACTIVE_FILTER = "activeFilter";
    private static ApplicationContext applicationContext;
    
    @Autowired
    private ActiveFilterUtils(ApplicationContext applicationContext) {
        ActiveFilterUtils.applicationContext = applicationContext;
    }

    public static void enable() {
        Optional.of(applicationContext.getBean(EntityManager.class))
                .map(em -> em.unwrap(Session.class))
                .ifPresent(ActiveFilterUtils::enable);
    }

    private static void enable(Session session) {
        if (session.getEnabledFilter(ACTIVE_FILTER) == null) {
            session.enableFilter(ACTIVE_FILTER).setParameter("isActive", true);
        }
    }

    public static void disable() {
        applicationContext.getBean(EntityManager.class)
                .unwrap(Session.class)
                .disableFilter(ACTIVE_FILTER);
    }
    
}
