package nl.fourscouts.blog.domainownership.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MeetingPlanned {
    String meetingId;

    LocalDateTime dateTime;

    String organizerId;
}
