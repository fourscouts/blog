package nl.fourscouts.blog.secureddomain.domain.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SalaryRaised {
	private String employeeId;

	private int raise;
	private int newSalary;
}
