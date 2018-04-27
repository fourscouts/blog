package nl.fourscouts.blog.validateddomain.projections;

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
@Table(name = "boxes")
@NoArgsConstructor(access = PRIVATE)
public class Box {
	@Id
	private String boxId;

	private int availableRoom;
}
