package nl.fourscouts.blog.secureddomain.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Entity
@AllArgsConstructor
@Table(name = "salaries")
@NoArgsConstructor(access = PRIVATE)
public class Salary {
	@Id
	private String employeeId;

	private int value;
}
