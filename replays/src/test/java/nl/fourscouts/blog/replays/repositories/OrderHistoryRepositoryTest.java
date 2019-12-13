package nl.fourscouts.blog.replays.repositories;

import nl.fourscouts.blog.replays.domain.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderHistoryRepositoryTest {
	@Autowired
	private OrderHistoryRepository repository;

	@Autowired
	private EntityManager manager;

	@Test
	void shouldSave() {
		repository.save(new OrderHistoryItem("reference", "customerId", LocalDateTime.now()));

		Optional<OrderHistoryItem> maybeItem = repository.findByReference("reference");

		assertThat(maybeItem).isPresent();
		OrderHistoryItem item = maybeItem.get();
		assertThat(item).isNotNull();
		assertThat(item.getStatus()).isEqualTo(Status.PLACED);
	}

	@Test
	void shouldUpdateStatus() {
		repository.save(new OrderHistoryItem("reference", "customerId", LocalDateTime.now()));
		repository.updateStatus("reference", Status.DISPATCHED);

		Optional<OrderHistoryItem> maybeItem = repository.findByReference("reference");

		assertThat(maybeItem).isPresent();
		assertThat(maybeItem.get().getStatus()).isEqualTo(Status.DISPATCHED);
	}
}
