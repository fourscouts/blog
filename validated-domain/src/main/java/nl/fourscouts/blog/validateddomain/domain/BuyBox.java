package nl.fourscouts.blog.validateddomain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuyBox {
	private String boxId;

	private int size;
}
