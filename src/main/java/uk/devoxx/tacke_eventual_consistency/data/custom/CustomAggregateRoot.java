package uk.devoxx.tacke_eventual_consistency.data.custom;

import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;

public class CustomAggregateRoot<A extends AbstractAggregateRoot<A>> extends AbstractAggregateRoot<A> {

    public Collection<Object> retrieveDomainEvents() {
        return domainEvents();
    }

    public void removeAllDomainEvents() {
        clearDomainEvents();
    }
}
