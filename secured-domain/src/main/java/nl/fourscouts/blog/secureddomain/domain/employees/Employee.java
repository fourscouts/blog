package nl.fourscouts.blog.secureddomain.domain.employees;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static lombok.AccessLevel.PRIVATE;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor(access = PRIVATE)
public class Employee {
	@AggregateIdentifier
	private String employeeId;

	private int salary;

	@CommandHandler
	public Employee(HireEmployee command) {
		apply(new EmployeeHired(command.getEmployeeId(), command.getName(), command.getSalary()));
	}

	@EventHandler
	public void onHired(EmployeeHired event) {
		employeeId = event.getEmployeeId();

		salary = event.getSalary();
	}

	@CommandHandler
	public void raiseSalary(RaiseSalary command) {
		apply(new SalaryRaised(employeeId, command.getRaise(), salary + command.getRaise()));
	}

	@EventHandler
	public void onSalaryRaised(SalaryRaised event) {
		salary = event.getNewSalary();
	}
}
