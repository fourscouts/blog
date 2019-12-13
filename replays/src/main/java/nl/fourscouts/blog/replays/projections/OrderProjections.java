package nl.fourscouts.blog.replays.projections;

import lombok.AllArgsConstructor;
import nl.fourscouts.blog.replays.domain.OrderPlaced;
import nl.fourscouts.blog.replays.domain.OrderStatusChanged;
import nl.fourscouts.blog.replays.repositories.OrderHistoryItem;
import nl.fourscouts.blog.replays.repositories.OrderHistoryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ReplayStatus;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@AllArgsConstructor
public class OrderProjections {
	private OrderHistoryRepository repository;

	@EventHandler
	public void onOrderPlaced(OrderPlaced event, @Timestamp Instant timestamp, ReplayStatus replayStatus) {
		repository.save(new OrderHistoryItem(event.getReference(), event.getCustomerId(), LocalDateTime.ofInstant(timestamp, ZoneOffset.UTC)));

		if (replayStatus.isReplay()) {
			try {
				// if we are replaying, build in a little delay so we can see the progress in action a bit better
				Thread.sleep(20L);
			} catch (InterruptedException e) { /* shouldn't happen */ }
		}
	}

	@EventHandler
	public void onOrderStatusChanged(OrderStatusChanged event) {
		repository.updateStatus(event.getReference(), event.getStatus());
	}

	@ResetHandler
	public void reset() {
		repository.deleteAll();
	}
}
