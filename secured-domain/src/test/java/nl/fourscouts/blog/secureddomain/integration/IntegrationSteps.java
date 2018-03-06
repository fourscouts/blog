package nl.fourscouts.blog.secureddomain.integration;

import nl.fourscouts.blog.secureddomain.domain.security.RoleRequired;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
class IntegrationSteps {
	@Autowired
	protected CommandGateway commandGateway;

	@Autowired
	protected IdentifierFactory identifierFactory;

	protected void sendCommand(Object command) {
		try {
			commandGateway.sendAndWait(command);
		} catch (RoleRequired exception) {}
	}
}
