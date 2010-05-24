package pl.softwaremill.cdiext.persistence;

import org.hibernate.FlushMode;
import org.hibernate.Session;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class EntityManagerProducer {
    @PersistenceContext
    private EntityManager entityManager;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Produces @RequestScoped @Writeable
    public EntityManager getEntityManager() {
        return new EntityManagerTxEnlistDecorator(entityManager);
    }

    @Produces @RequestScoped @ReadOnly
    public EntityManager getReadOnlyEntityManager() {
        EntityManager readOnlyEntityManager = entityManagerFactory.createEntityManager();

        Session readOnlySession = (Session) readOnlyEntityManager.getDelegate();
        readOnlySession.setDefaultReadOnly(true);
        readOnlySession.setFlushMode(FlushMode.MANUAL);

        return new EntityManagerTxEnlistDecorator(readOnlyEntityManager);
    }

    public void disposeOfReadOnlyEntityManager(@Disposes @ReadOnly EntityManager readOnlyEntityManager) {
        readOnlyEntityManager.close();
    }
}