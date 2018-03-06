package nl.fourscouts.blog.secureddomain.projections;

import nl.fourscouts.blog.secureddomain.domain.employees.EmployeeHired;
import nl.fourscouts.blog.secureddomain.domain.employees.SalaryRaised;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SalaryProjections {
	private SalaryRepository salaryRepository;

	@Autowired
	public SalaryProjections(SalaryRepository salaryRepository) {
		this.salaryRepository = salaryRepository;
	}

	@EventHandler
	public void onEmployeeHired(EmployeeHired event) {
		salaryRepository.save(new Salary(event.getEmployeeId(), event.getSalary()));
	}

	@EventHandler
	public void onSalaryRaised(SalaryRaised event) {
		salaryRepository.update(event.getEmployeeId(), event.getNewSalary());
	}
}
