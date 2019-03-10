package nl.fourscouts.blog.deadlinemanager.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Reservation {
	private String reservationId;

	private int amountOfTickets;

	private String deadlineId;
}
