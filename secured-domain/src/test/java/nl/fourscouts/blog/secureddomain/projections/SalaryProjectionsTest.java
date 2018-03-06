package nl.fourscouts.blog.secureddomain.projections;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.SimpleEventHandlerInvoker;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.EMPLOYEE_ID;
import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.INITIAL_SALARY;
import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.NEW_SALARY;
import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.employeeHired;
import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.salaryRaised;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan
@RunWith(SpringRunner.class)
public class SalaryProjectionsTest {
	@Autowired
	private SalaryProjections projections;

	@Autowired
	private SalaryRepository repository;

	private EventBus eventBus;

	private SubscribingEventProcessor eventProcessor;

	@Before
	public void createEventBus() {
		eventBus = new SimpleEventBus();

		eventProcessor = new SubscribingEventProcessor("listener", new SimpleEventHandlerInvoker(projections), eventBus);
		eventProcessor.start();
	}

	@After
	public void stopEventListener() {
		eventProcessor.shutDown();
	}

	@Test
	public void shouldSaveSalaryOfNewEmployee() {
		publish(employeeHired());

		assertThat(repository.findById(EMPLOYEE_ID)).map(Salary::getValue).contains(INITIAL_SALARY);
	}

	@Test
	public void shouldUpdateSalaryUponRaise() {
		publish(employeeHired(), salaryRaised());

		assertThat(repository.findById(EMPLOYEE_ID)).map(Salary::getValue).contains(NEW_SALARY);
	}

	private void publish(Object... events) {
		Arrays.stream(events).forEach(event -> eventBus.publish(GenericEventMessage.asEventMessage(event)));
	}
}