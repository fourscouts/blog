package nl.fourscouts.blog.replays.domain;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class PlaceOrder {
	@TargetAggregateIdentifier
	private String reference;

	private String customerId;
}
