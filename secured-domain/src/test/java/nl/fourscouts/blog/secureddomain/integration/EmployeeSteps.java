package nl.fourscouts.blog.secureddomain.integration;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.fourscouts.blog.secureddomain.domain.employees.HireEmployee;
import nl.fourscouts.blog.secureddomain.domain.employees.RaiseSalary;
import nl.fourscouts.blog.secureddomain.projections.Salary;
import nl.fourscouts.blog.secureddomain.projections.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static nl.fourscouts.blog.secureddomain.domain.employees.EmployeeEvents.EMPLOYEE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeSteps extends IntegrationSteps {
	@Autowired
	private SalaryRepository salaryRepository;

	@Given("employee ([A-Z]) with salary (\\d+)")
	public void employeeWithSalary(String employeeId, int salary) {
		commandGateway.sendAndWait(new HireEmployee(employeeId, EMPLOYEE_NAME, salary));
	}

	@When("the user raises ([A-Z])'s salary with (\\d+)")
	public void salaryIsRaised(String employeeId, int raise) {
		sendCommand(new RaiseSalary(employeeId, raise));
	}

	@Then("the salary of employee ([A-Z]) is (\\d+)")
	public void checkSalary(String employeeId, int salary) {
		assertThat(salaryRepository.findById(employeeId)).map(Salary::getValue).contains(salary);
	}
}
