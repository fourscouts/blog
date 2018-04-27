package nl.fourscouts.blog.validateddomain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemsAdded {
	private String boxId;

	private int count;
	private int availableRoom;
}
