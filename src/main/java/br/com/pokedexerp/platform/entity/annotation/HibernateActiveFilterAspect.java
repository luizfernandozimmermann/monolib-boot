package br.com.pokedexerp.platform.entity.annotation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
public class HibernateActiveFilterAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Before("@annotation(transactional)")
    public void enableFilter(Transactional transactional) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            return;
        }

        var session = entityManager.unwrap(Session.class);

        if (session.getEnabledFilter("activeFilter") == null) {
            session.enableFilter("activeFilter").setParameter("isActive", true);
        }
    }

}
