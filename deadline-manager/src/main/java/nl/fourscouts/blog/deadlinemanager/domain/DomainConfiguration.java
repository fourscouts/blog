package nl.fourscouts.blog.deadlinemanager.domain;

import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {
	@Bean
	public EventStorageEngine eventStorageEngine() {
		return new InMemoryEventStorageEngine();
	}

	@Bean
	public DeadlineManager deadlineManager(AxonConfiguration configuration) {
		return SimpleDeadlineManager.builder().scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration)).build();
	}
}
