package monolib.data.aspect;

import monolib.data.utils.ActiveFilterUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
public class HibernateActiveFilterAspect {

    @Before("@annotation(transactional)")
    public void enableFilter(Transactional transactional) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            return;
        }
        ActiveFilterUtils.enable();
    }

}
