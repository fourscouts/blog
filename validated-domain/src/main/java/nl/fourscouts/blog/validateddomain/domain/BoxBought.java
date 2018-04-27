package nl.fourscouts.blog.validateddomain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoxBought {
	private String boxId;

	private int size;
}
