package nl.fourscouts.blog.deadlinemanager.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.AVAILABLE_TICKETS;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.NAME;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.RESERVATION_ID;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.RESERVED_TICKETS;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.SHOWTIME;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.SHOW_ID;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.reservationConfirmed;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.reservationExpired;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.showPlanned;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.ticketsReserved;

public class ShowTest {
	private FixtureConfiguration<Show> fixture;

	@Before
	public void createFixture() {
		fixture = new AggregateTestFixture<>(Show.class);
	}

	@Test
	public void shouldPlan() {
		fixture
			.givenNoPriorActivity()
			.when(new PlanShow(SHOW_ID, NAME, SHOWTIME, AVAILABLE_TICKETS))
			.expectEvents(showPlanned());
	}

	@Test
	public void shouldReserveTickets() {
		fixture
			.given(showPlanned())
			.when(new ReserveTickets(SHOW_ID, RESERVATION_ID, RESERVED_TICKETS))
			.expectEvents(ticketsReserved())
			.expectScheduledDeadline(Duration.ofMinutes(15), RESERVATION_ID);
	}

	@Test
	public void shouldNotMakeDoubleReservation() {
		fixture
			.given(showPlanned(), ticketsReserved())
			.when(new ReserveTickets(SHOW_ID, RESERVATION_ID, RESERVED_TICKETS))
			.expectNoEvents()
			.expectNoScheduledDeadlines();
	}

	@Test
	public void shouldNotMakeExcessiveReservation() {
		fixture
			.given(showPlanned())
			.when(new ReserveTickets(SHOW_ID, RESERVATION_ID, AVAILABLE_TICKETS + 1))
			.expectNoEvents()
			.expectNoScheduledDeadlines();
	}

	@Test
	public void shouldExpireReservationAfterDeadlineExpires() {
		fixture
			.given(showPlanned())
			.andGivenCommands(new ReserveTickets(SHOW_ID, RESERVATION_ID, RESERVED_TICKETS))
			.whenThenTimeElapses(Duration.ofMinutes(16))
			.expectDeadlinesMet(RESERVATION_ID)
			.expectEvents(reservationExpired());
	}

	@Test
	public void shouldCancelReservationExpirationWhenReservationConfirmed() {
		fixture
			.given(showPlanned())
			.andGivenCommands(new ReserveTickets(SHOW_ID, RESERVATION_ID, RESERVED_TICKETS))
			.when(new ConfirmReservation(SHOW_ID, RESERVATION_ID))
			.expectEvents(reservationConfirmed())
			.expectNoScheduledDeadlines();
	}
}
