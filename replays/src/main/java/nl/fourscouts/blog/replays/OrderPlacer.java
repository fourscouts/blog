package nl.fourscouts.blog.replays;

import nl.fourscouts.blog.replays.domain.DispatchOrder;
import nl.fourscouts.blog.replays.domain.PlaceOrder;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class OrderPlacer implements ApplicationListener<ApplicationReadyEvent> {
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		CommandGateway commandGateway = event.getApplicationContext().getBean(CommandGateway.class);

		// generate a bunch of test orders
		IntStream.range(0, 1000).forEach(order -> {
			String reference = String.format("order-%d", order);

			commandGateway.send(new PlaceOrder(reference, "customerId"));
			commandGateway.send(new DispatchOrder(reference));
		});
	}
}
