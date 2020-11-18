package nl.fourscouts.blog.domainownership.domain;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.DefaultIdentifierFactory;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {
    @Bean
    public IdentifierFactory identifierFactory() {
        return new DefaultIdentifierFactory();
    }

    @Bean
    public EventStorageEngine eventStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Autowired
    public void registerInterceptors(CommandGateway commandGateway, UserIdDispatcher userIdDispatcher) {
        commandGateway.registerDispatchInterceptor(userIdDispatcher);
    }
}
