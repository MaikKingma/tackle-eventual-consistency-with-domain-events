package uk.devoxx.tackle_eventual_consistency.data.baserepository;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.lang.NonNull;
import uk.devoxx.tackle_eventual_consistency.domaininteraction.kafka.KafkaProducerService;

import java.io.Serializable;

public class BaseJpaRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {

    private final ApplicationEventPublisher eventPublisher;
    private final KafkaProducerService kafkaProducerService;

    public BaseJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface,
                                        ApplicationEventPublisher eventPublisher,
                                        KafkaProducerService kafkaProducerService) {
        super(repositoryInterface);
        this.eventPublisher = eventPublisher;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(@NonNull EntityManager entityManager) {
        return new BaseJpaRepositoryFactory(entityManager, eventPublisher, kafkaProducerService);
    }
}
