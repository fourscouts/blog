package nl.fourscouts.blog.deadlinemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PlanShow {
	@TargetAggregateIdentifier
	private String showId;

	private LocalDateTime time;

	private int availableTickets;
}
