package nl.fourscouts.blog.deadlinemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReserveTickets {
	@TargetAggregateIdentifier
	private String showId;

	private String reservationId;

	private int amount;
}
