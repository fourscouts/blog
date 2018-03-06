package nl.fourscouts.blog.secureddomain.domain.employees;

public class EmployeeEvents {
	public static EmployeeHired employeeHired() {
		return new EmployeeHired(EMPLOYEE_ID, EMPLOYEE_NAME, INITIAL_SALARY);
	}

	public static SalaryRaised salaryRaised() {
		return new SalaryRaised(EMPLOYEE_ID, SALARY_RAISE, NEW_SALARY);
	}

	public static String EMPLOYEE_ID = "employeeId";

	public static String EMPLOYEE_NAME = "Tony";

	public static int INITIAL_SALARY = 2300;
	public static int SALARY_RAISE = 200;
	public static int NEW_SALARY = 2500;
}
