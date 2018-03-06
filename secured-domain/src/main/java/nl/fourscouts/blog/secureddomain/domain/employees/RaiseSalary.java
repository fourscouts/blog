package nl.fourscouts.blog.secureddomain.domain.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.fourscouts.blog.secureddomain.domain.security.RequiresManager;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@RequiresManager
@AllArgsConstructor
public class RaiseSalary {
	@TargetAggregateIdentifier
	private String employeeId;

	private int raise;
}
