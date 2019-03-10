package nl.fourscouts.blog.deadlinemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketsReserved {
	private String showId;

	private String reservationId;

	private int amount;
}
