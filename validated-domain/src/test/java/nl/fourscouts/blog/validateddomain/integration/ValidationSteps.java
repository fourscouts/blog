package nl.fourscouts.blog.validateddomain.integration;

import cucumber.api.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationSteps extends IntegrationSteps {
	@Then("validation fails")
	public void checkValidationResult() {
		assertThat(VALIDATION_EXCEPTION_CONTAINER.get()).isNotNull();
	}
}
