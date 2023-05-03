package uk.devoxx.tackle_eventual_consistency.data.baserepository;

import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;

public class AggregateRoot<A extends AbstractAggregateRoot<A>> extends AbstractAggregateRoot<A> {

    public Collection<Object> retrieveDomainEvents() {
        return domainEvents();
    }

    public void removeAllDomainEvents() {
        clearDomainEvents();
    }
}
