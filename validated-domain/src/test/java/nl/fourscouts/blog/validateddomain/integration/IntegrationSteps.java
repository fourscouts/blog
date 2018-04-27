package nl.fourscouts.blog.validateddomain.integration;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
abstract class IntegrationSteps {
	@Autowired
	protected CommandGateway commandGateway;

	@Autowired
	protected IdentifierFactory identifierFactory;

	protected void sendCommand(Object command) {
		try {
			commandGateway.sendAndWait(command);

			VALIDATION_EXCEPTION_CONTAINER.remove();
		} catch (JSR303ViolationException exception) {
			VALIDATION_EXCEPTION_CONTAINER.set(exception);
		}
	}

	protected final static ThreadLocal<JSR303ViolationException> VALIDATION_EXCEPTION_CONTAINER = new ThreadLocal<>();
}
