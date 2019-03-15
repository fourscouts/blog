package nl.fourscouts.blog.deadlinemanager;

import lombok.extern.slf4j.Slf4j;
import nl.fourscouts.blog.deadlinemanager.domain.ConfirmReservation;
import nl.fourscouts.blog.deadlinemanager.domain.PlanShow;
import nl.fourscouts.blog.deadlinemanager.domain.ReservationConfirmed;
import nl.fourscouts.blog.deadlinemanager.domain.ReservationExpired;
import nl.fourscouts.blog.deadlinemanager.domain.ReserveTickets;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class Server {
	public static void main(String... arguments) {
		SpringApplication.run(Server.class, arguments);
	}

	@Bean
	public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
		return event -> {
			CommandGateway commandGateway = event.getApplicationContext().getBean(CommandGateway.class);

			String showId = "showId";

			// plan the show
			commandGateway.sendAndWait(new PlanShow(showId, "Drake - the assassination vacation tour", LocalDateTime.parse("2019-04-26T20:00:00"), 17000));

			// make a few reservations
			commandGateway.sendAndWait(new ReserveTickets(showId, "first-reservation", 3));
			commandGateway.sendAndWait(new ReserveTickets(showId, "second-reservation", 2));

			// confirm one - the other will eventually lapse
			commandGateway.sendAndWait(new ConfirmReservation(showId, "second-reservation"));
		};
	}

	@EventHandler
	public void onReservationConfirmed(ReservationConfirmed event) {
		log.info("Reservation '{}' confirmed! Email the tickets.", event.getReservationId());
	}

	@EventHandler
	public void onReservationExpired(ReservationExpired event) {
		log.info("Reservation '{}' expired! Reserved tickets are for sale again.", event.getReservationId());
	}
}
