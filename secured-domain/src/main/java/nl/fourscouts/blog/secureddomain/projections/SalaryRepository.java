package nl.fourscouts.blog.secureddomain.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalaryRepository extends JpaRepository<Salary, String> {
	@Modifying(clearAutomatically = true)
	@Query("update Salary set value = :value where employeeId = :employeeId")
	void update(@Param("employeeId") String employeeId, @Param("value") int value);
}
