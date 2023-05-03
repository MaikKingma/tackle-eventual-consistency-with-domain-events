package uk.devoxx.tackle_eventual_consistency.data.baserepository;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;

public class BaseJpaRepositoryFactory extends JpaRepositoryFactory {

    private final EntityManager entityManager;
    private final ApplicationEventPublisher eventPublisher;

    public BaseJpaRepositoryFactory(EntityManager entityManager, ApplicationEventPublisher messageLocale) {
        super(entityManager);
        this.entityManager = entityManager;
        this.eventPublisher = messageLocale;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        return new BaseJpaRepositoryImpl<>(getEntityInformation(information.getDomainType()), entityManager, eventPublisher);
    }
}
