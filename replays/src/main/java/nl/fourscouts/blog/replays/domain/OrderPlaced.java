package nl.fourscouts.blog.replays.domain;

import lombok.Value;

@Value
public class OrderPlaced {
	private String reference;

	private String customerId;
}
