package uk.devoxx.tacke_eventual_consistency.data.custom;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class MyJpaRepositoryFactory extends JpaRepositoryFactory {

    private final EntityManager entityManager;
    private final ApplicationEventPublisher eventPublisher;

    public MyJpaRepositoryFactory(EntityManager entityManager, ApplicationEventPublisher messageLocale) {
        super(entityManager);
        this.entityManager = entityManager;
        this.eventPublisher = messageLocale;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        return new ExtendedRepositoryImpl<>(getEntityInformation(information.getDomainType()), entityManager, eventPublisher);
    }
}
