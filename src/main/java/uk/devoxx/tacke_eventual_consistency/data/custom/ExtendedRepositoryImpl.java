package uk.devoxx.tacke_eventual_consistency.data.custom;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

public class ExtendedRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID> {

    private static final Logger log = getLogger(ExtendedRepositoryImpl.class);

    private final EntityManager entityManager;
    private ApplicationEventPublisher eventPublisher;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public <S extends T> S save(S entity) {
        if (entity instanceof CustomAggregateRoot) {
            log.info("using custom save method");
            S savedEntity = super.save(entity);
            // publishDomainEvents((CustomAggregateRoot<?>) entity);
            return savedEntity;
        } else {
            return super.save(entity);
        }
    }

    private void publishDomainEvents(CustomAggregateRoot<?> aggregate) {
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
