package nl.fourscouts.blog.deadlinemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationExpired {
	private String reservationId;
}
