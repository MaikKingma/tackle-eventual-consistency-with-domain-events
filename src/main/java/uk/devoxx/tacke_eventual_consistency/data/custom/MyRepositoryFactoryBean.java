package uk.devoxx.tacke_eventual_consistency.data.custom;

import jakarta.persistence.EntityManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.lang.NonNull;

import java.io.Serializable;

public class MyRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {

    private final ApplicationEventPublisher eventPublisher;

    public MyRepositoryFactoryBean(Class<? extends T> repositoryInterface, ApplicationEventPublisher eventPublisher) {
        super(repositoryInterface);
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(@NonNull EntityManager entityManager) {
        return new MyJpaRepositoryFactory(entityManager, eventPublisher);
    }
}
