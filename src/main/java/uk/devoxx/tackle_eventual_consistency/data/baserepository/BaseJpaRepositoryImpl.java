package uk.devoxx.tackle_eventual_consistency.data.baserepository;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.kafka.KafkaProducerService;

import java.io.Serializable;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

public class BaseJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

    private static final Logger log = getLogger(BaseJpaRepositoryImpl.class);

    private final EntityManager entityManager;
    private final ApplicationEventPublisher eventPublisher;
    private final KafkaProducerService kafkaProducerService;

    public BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager,
                                 ApplicationEventPublisher eventPublisher, KafkaProducerService kafkaProducerService) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.eventPublisher = eventPublisher;
        this.kafkaProducerService = kafkaProducerService;
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
                domainEvents.forEach(event -> {
                    kafkaProducerService.sendMessage(event.getClass().toString(), event.toString());
                    eventPublisher.publishEvent(event);
                });
                aggregate.removeAllDomainEvents();
            }
        });
    }
}
