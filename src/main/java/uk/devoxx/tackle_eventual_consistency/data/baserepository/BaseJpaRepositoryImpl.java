package uk.devoxx.tackle_eventual_consistency.data.baserepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.kafka.KafkaProducerService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

public class BaseJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

    private static final Logger log = getLogger(BaseJpaRepositoryImpl.class);

    private final EntityManager entityManager;
    private final KafkaProducerService kafkaProducerService;

    public BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                                 EntityManager entityManager,
                                 KafkaProducerService kafkaProducerService) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        if (entity instanceof AggregateRoot) {
            Collection<Object> events = ((AggregateRoot<?>) entity).retrieveDomainEvents();
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                private final Collection<Object> domainEvents = new ArrayList<>(events);
                @Override
                public void afterCommit() {
                    domainEvents.forEach(event -> {
                        String topic = event.getClass().getSimpleName().toLowerCase();
                        log.info("publishing to topic {} the event: {}", topic, event);
                        kafkaProducerService.sendMessage(topic, event.toString());
                    });
                }
            });
        }
        return super.save(entity);
    }
}
