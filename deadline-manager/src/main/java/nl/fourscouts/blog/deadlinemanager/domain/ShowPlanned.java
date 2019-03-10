package nl.fourscouts.blog.deadlinemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShowPlanned {
	private String showId;

	private LocalDateTime time;

	private int availableTickets;
}
