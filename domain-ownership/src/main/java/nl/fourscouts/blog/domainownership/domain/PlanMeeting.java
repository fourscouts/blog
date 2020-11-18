package nl.fourscouts.blog.domainownership.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PlanMeeting {
    String meetingId;

    LocalDateTime dateTime;
}
