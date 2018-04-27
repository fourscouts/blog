package nl.fourscouts.blog.validateddomain.domain;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class DomainConfiguration {
	@Bean
	public IdentifierFactory identifierFactory() {
		return IdentifierFactory.getInstance();
	}

	@Autowired
	public void validationInterceptor(CommandBus commandBus, LocalValidatorFactoryBean validatorFactory) {
		((SimpleCommandBus) commandBus).registerDispatchInterceptor(new BeanValidationInterceptor<>(validatorFactory));
	}
}
