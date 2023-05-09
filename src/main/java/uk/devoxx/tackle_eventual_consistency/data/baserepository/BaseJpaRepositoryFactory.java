package uk.devoxx.tackle_eventual_consistency.data.baserepository;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.kafka.KafkaProducerService;

public class BaseJpaRepositoryFactory extends JpaRepositoryFactory {

    private final EntityManager entityManager;
    private final ApplicationEventPublisher eventPublisher;
    private final KafkaProducerService kafkaProducerService;

    public BaseJpaRepositoryFactory(EntityManager entityManager,
                                    ApplicationEventPublisher messageLocale,
                                    KafkaProducerService kafkaProducerService) {
        super(entityManager);
        this.entityManager = entityManager;
        this.eventPublisher = messageLocale;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        return new BaseJpaRepositoryImpl<>(getEntityInformation(information.getDomainType()), entityManager,
                eventPublisher, kafkaProducerService);
    }
}
