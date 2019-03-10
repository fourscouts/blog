package nl.fourscouts.blog.deadlinemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class ConfirmReservation {
	@TargetAggregateIdentifier
	private String showId;

	private String reservationId;
}
