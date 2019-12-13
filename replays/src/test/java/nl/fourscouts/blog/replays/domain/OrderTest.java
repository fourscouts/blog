package nl.fourscouts.blog.replays.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {
	private FixtureConfiguration<Order> fixture;

	@BeforeEach
	void createFixture() {
		fixture = new AggregateTestFixture<>(Order.class);
	}

	@Test
	void shouldPlace() {
		fixture
			.givenNoPriorActivity()
			.when(new PlaceOrder("reference", "customerId"))
			.expectEvents(new OrderPlaced("reference", "customerId"));
	}

	@Test
	void shouldDispatch() {
		fixture
			.given(new OrderPlaced("reference", "customerId"))
			.when(new DispatchOrder("reference"))
			.expectEvents(new OrderStatusChanged("reference", Status.DISPATCHED));
	}
}
