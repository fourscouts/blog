package nl.fourscouts.blog.replays.domain;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class DispatchOrder {
	@TargetAggregateIdentifier
	private String reference;
}
