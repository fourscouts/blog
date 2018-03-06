package nl.fourscouts.blog.secureddomain.domain.employees;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.EMPLOYEE_ID;
import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.EMPLOYEE_NAME;
import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.INITIAL_SALARY;
import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.SALARY_RAISE;
import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.employeeHired;
import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.salaryRaised;

public class EmployeeTest {
	private FixtureConfiguration<Employee> fixture;

	@Before
	public void createFixture() {
		fixture = new AggregateTestFixture<>(Employee.class);
	}

	@Test
	public void shouldBeHireable() {
		fixture
			.givenNoPriorActivity()
			.when(new HireEmployee(EMPLOYEE_ID, EMPLOYEE_NAME, INITIAL_SALARY))
			.expectEvents(employeeHired());
	}

	@Test
	public void shouldHaveSalaryRaised() {
		fixture
			.given(employeeHired())
			.when(new RaiseSalary(EMPLOYEE_ID, SALARY_RAISE))
			.expectEvents(salaryRaised());
	}
}