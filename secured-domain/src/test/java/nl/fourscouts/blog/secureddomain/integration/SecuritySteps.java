package nl.fourscouts.blog.secureddomain.integration;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Collections.singletonList;

public class SecuritySteps extends IntegrationSteps {
	@Given("the user is a manager")
	public void userIsManager() {
		Authentication authentication = new TestingAuthenticationToken("Martha", "secret", singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Given("the user is an employee")
	public void userIsEmployee() {
		Authentication authentication = new TestingAuthenticationToken("Eric", "exp?zworp", singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@After
	public void logOut() {
		SecurityContextHolder.clearContext();
	}
}
