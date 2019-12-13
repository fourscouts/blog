package nl.fourscouts.blog.replays.projections;

import nl.fourscouts.blog.replays.domain.OrderPlaced;
import nl.fourscouts.blog.replays.domain.OrderStatusChanged;
import nl.fourscouts.blog.replays.domain.Status;
import nl.fourscouts.blog.replays.repositories.OrderHistoryRepository;
import org.axonframework.eventhandling.ReplayStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderProjectionsTest {
	@Mock
	private OrderHistoryRepository repository;

	@InjectMocks
	private OrderProjections projections;

	@Test
	public void shouldSave() {
		projections.onOrderPlaced(new OrderPlaced("reference", "customerId"), Instant.now(), ReplayStatus.REGULAR);

		verify(repository).save(any());
	}

	@Test
	public void shouldUpdateStatus() {
		projections.onOrderStatusChanged(new OrderStatusChanged("reference", Status.DISPATCHED));

		verify(repository).updateStatus("reference", Status.DISPATCHED);
	}
}
