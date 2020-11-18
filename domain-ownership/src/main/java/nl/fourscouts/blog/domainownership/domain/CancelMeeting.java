package nl.fourscouts.blog.domainownership.domain;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelMeeting {
    @TargetAggregateIdentifier
    String meetingId;
}
