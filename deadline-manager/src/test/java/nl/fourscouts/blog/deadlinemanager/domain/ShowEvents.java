package nl.fourscouts.blog.deadlinemanager.domain;

import java.time.LocalDateTime;

public class ShowEvents {
	public static final String SHOW_ID = "showId";

	public static final String NAME = "Drake - the assassination vacation tour";

	public static final LocalDateTime SHOWTIME = LocalDateTime.parse("2019-05-01T14:00:00");

	public static final int AVAILABLE_TICKETS = 10000;

	public static final String RESERVATION_ID = "reservationId";

	public static final int RESERVED_TICKETS = 5;

	public static ShowPlanned showPlanned() {
		return new ShowPlanned(SHOW_ID, NAME, SHOWTIME, AVAILABLE_TICKETS);
	}

	public static TicketsReserved ticketsReserved() {
		return new TicketsReserved(SHOW_ID, RESERVATION_ID, RESERVED_TICKETS);
	}

	public static ReservationConfirmed reservationConfirmed() {
		return new ReservationConfirmed(RESERVATION_ID);
	}

	public static ReservationExpired reservationExpired() {
		return new ReservationExpired(RESERVATION_ID);
	}
}
