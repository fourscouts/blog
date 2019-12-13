package nl.fourscouts.blog.replays.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@AggregateIdentifier
	private String reference;

	@CommandHandler
	public Order(PlaceOrder command) {
		apply(new OrderPlaced(command.getReference(), command.getCustomerId()));
	}

	@EventHandler
	public void onPlaced(OrderPlaced event) {
		reference = event.getReference();
	}

	@CommandHandler
	public void dispatch(DispatchOrder command) {
		apply(new OrderStatusChanged(reference, Status.DISPATCHED));
	}
}
