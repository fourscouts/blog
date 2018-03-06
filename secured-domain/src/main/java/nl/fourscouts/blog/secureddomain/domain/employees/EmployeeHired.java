package nl.fourscouts.blog.secureddomain.domain.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeHired {
	private String employeeId;

	private String name;
	private int salary;
}
