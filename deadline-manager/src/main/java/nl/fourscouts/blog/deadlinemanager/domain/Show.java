package nl.fourscouts.blog.deadlinemanager.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Getter
@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Show {
	@AggregateIdentifier
	private String showId;

	private LocalDateTime time;
	private int availableTickets;

	private Map<String, Reservation> reservations;

	@CommandHandler
	public Show(PlanShow command) {
		apply(new ShowPlanned(command.getShowId(), command.getTime(), command.getAvailableTickets()));
	}

	@EventHandler
	public void onPlanned(ShowPlanned event) {
		showId = event.getShowId();

		time = event.getTime();
		availableTickets = event.getAvailableTickets();

		reservations = new HashMap<>();
	}

	@CommandHandler
	public void reserveTickets(ReserveTickets command, DeadlineManager deadlineManager) {
		if (availableTickets >= command.getAmount()) {
			String reservationDeadlineId = deadlineManager.schedule(Duration.ofMinutes(15), "reservation", command.getReservationId());

			apply(new TicketsReserved(command.getShowId(), command.getReservationId(), command.getAmount()), MetaData.with("reservationDeadlineId", reservationDeadlineId));
		}
	}

	@EventHandler
	public void onTicketsReserved(TicketsReserved event, @MetaDataValue("reservationDeadlineId") String reservationDeadlineId) {
		availableTickets -= event.getAmount();

		reservations.put(event.getReservationId(), new Reservation(event.getReservationId(), event.getAmount(), reservationDeadlineId));
	}

	@CommandHandler
	public void confirmReservation(ConfirmReservation command, DeadlineManager deadlineManager) {
		deadlineManager.cancelSchedule("reservation", reservations.get(command.getReservationId()).getDeadlineId());

		apply(new ReservationConfirmed(command.getReservationId()));
	}

	@DeadlineHandler
	public void handleReservationExpired(String reservationId) {
		apply(new ReservationExpired(reservationId));
	}

	@EventHandler
	public void onReservationExpired(ReservationExpired event) {
		Reservation reservation = reservations.remove(event.getReservationId());

		availableTickets += reservation.getAmountOfTickets();
	}

	@EventHandler
	public void onReservationConfirmed(ReservationConfirmed event) {
		reservations.remove(event.getReservationId());
	}
}
