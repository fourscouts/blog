package nl.fourscouts.blog.domainownership.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CommandHandlerInterceptor;
import org.axonframework.spring.stereotype.Aggregate;

import static nl.fourscouts.blog.domainownership.domain.UserIdDispatcher.USER_ID;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {
    @AggregateIdentifier
    private String meetingId;

    private String organizerId;

    @CommandHandler
    public Meeting(PlanMeeting command, @NonNull @MetaDataValue(USER_ID) String userId) {
        apply(new MeetingPlanned(command.getMeetingId(), command.getDateTime(), userId));
    }

    @EventSourcingHandler
    public void onPlanned(MeetingPlanned event) {
        meetingId = event.getMeetingId();

        organizerId = event.getOrganizerId();
    }

    @CommandHandler
    public void cancel(CancelMeeting command) {
        apply(new MeetingCancelled(command.getMeetingId()));
    }

    @EventSourcingHandler
    public void onCancelled(MeetingCancelled event) {
        AggregateLifecycle.markDeleted();
    }

    @CommandHandlerInterceptor
    public void checkOwnership(CommandMessage<?> command, InterceptorChain chain) throws Exception {
        if (organizerId == command.getMetaData().get(USER_ID)) {
            chain.proceed();
        } else {
            throw new AccessDenied();
        }
    }
}
