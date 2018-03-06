package nl.fourscouts.blog.secureddomain.domain.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class HireEmployee {
	@TargetAggregateIdentifier
	private String employeeId;

	private String name;
	private int salary;
}
