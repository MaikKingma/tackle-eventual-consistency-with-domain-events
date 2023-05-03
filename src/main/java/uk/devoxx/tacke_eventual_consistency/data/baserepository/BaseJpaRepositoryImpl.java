package uk.devoxx.tacke_eventual_consistency.data.baserepository;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

public class BaseJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

    private static final Logger log = getLogger(BaseJpaRepositoryImpl.class);

    private final EntityManager entityManager;
    private final ApplicationEventPublisher eventPublisher;

    public BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager,
                                 ApplicationEventPublisher eventPublisher) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public <S extends T> S save(S entity) {
        if (entity instanceof AggregateRoot) {
            log.info("using custom save method");
            S savedEntity = super.save(entity);
            publishDomainEvents((AggregateRoot<?>) entity);
            return savedEntity;
        } else {
            return super.save(entity);
        }
    }

    private void publishDomainEvents(AggregateRoot<?> aggregate) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                Collection<Object> domainEvents = aggregate.retrieveDomainEvents();
                domainEvents.forEach(eventPublisher::publishEvent);
                aggregate.removeAllDomainEvents();
            }
        });
    }
}
