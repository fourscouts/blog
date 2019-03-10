package nl.fourscouts.blog.deadlinemanager.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.axonframework.test.aggregate.ResultValidator;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static nl.fourscouts.blog.deadlinemanager.domain.ShowEvents.AVAILABLE_TICKETS;
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
			.when(new PlanShow(SHOW_ID, SHOWTIME, AVAILABLE_TICKETS))
			.expectEvents(showPlanned())
			.expectState(show -> {
				assertEquals(SHOW_ID, show.getShowId());
				assertEquals(SHOWTIME, show.getTime());
				assertEquals(AVAILABLE_TICKETS, show.getAvailableTickets());
			});
	}

	@Test
	public void shouldReserveTickets() {
		fixture
			.given(showPlanned())
			.when(new ReserveTickets(SHOW_ID, RESERVATION_ID, RESERVED_TICKETS))
			.expectEvents(ticketsReserved())
			.expectState(show -> {
				assertEquals(AVAILABLE_TICKETS - RESERVED_TICKETS, show.getAvailableTickets());
				assertTrue(show.getReservations().containsKey(RESERVATION_ID));
			})
			.expectScheduledDeadline(Duration.ofMinutes(15), RESERVATION_ID);
	}

	@Test
	public void shouldNotMakeExcessiveReservation() {
		fixture
			.given(showPlanned())
			.when(new ReserveTickets(SHOW_ID, RESERVATION_ID, AVAILABLE_TICKETS + 1))
			.expectNoEvents()
			.expectState(show -> {
				assertEquals(AVAILABLE_TICKETS, show.getAvailableTickets());
				assertFalse(show.getReservations().containsKey(RESERVATION_ID));
			})
			.expectNoScheduledDeadlines();
	}

	@Test
	public void shouldExpireReservationAfterDeadlineExpires() {
		@SuppressWarnings("unchecked")
		ResultValidator<Show> validator = fixture
			.given(showPlanned())
			.andGivenCommands(new ReserveTickets(SHOW_ID, RESERVATION_ID, RESERVED_TICKETS))
			.andThenTimeElapses(Duration.ofMinutes(16))
			.expectDeadlinesMet(RESERVATION_ID);

		validator.expectState(show -> {
			assertEquals(AVAILABLE_TICKETS, show.getAvailableTickets());
			assertFalse(show.getReservations().containsKey(RESERVATION_ID));
		});
	}

	@Test
	public void shouldCancelReservationExpirationWhenReservationConfirmed() {
		fixture
			.given(showPlanned())
			.andGivenCommands(new ReserveTickets(SHOW_ID, RESERVATION_ID, RESERVED_TICKETS))
			.when(new ConfirmReservation(SHOW_ID, RESERVATION_ID))
			.expectEvents(reservationConfirmed())
			.expectState(show -> {
				assertFalse(show.getReservations().containsKey(RESERVATION_ID));
			}).expectNoScheduledDeadlines();
	}
}
