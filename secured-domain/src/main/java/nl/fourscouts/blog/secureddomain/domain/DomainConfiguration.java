package nl.fourscouts.blog.secureddomain.domain;

import nl.fourscouts.blog.secureddomain.domain.security.RoleInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {
	@Bean
	public IdentifierFactory identifierFactory() {
		return IdentifierFactory.getInstance();
	}

	@Autowired
	public void roleInterceptor(CommandBus commandBus, RoleInterceptor roleInterceptor) {
		((SimpleCommandBus) commandBus).registerHandlerInterceptor(roleInterceptor);
	}
}
