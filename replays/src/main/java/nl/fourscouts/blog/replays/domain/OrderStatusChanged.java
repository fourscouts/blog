package nl.fourscouts.blog.replays.domain;

import lombok.Value;

@Value
public class OrderStatusChanged {
	private String reference;

	private Status status;
}
