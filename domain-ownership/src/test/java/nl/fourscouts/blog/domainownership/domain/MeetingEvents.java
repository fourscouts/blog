package nl.fourscouts.blog.domainownership.domain;

import java.time.LocalDateTime;

public class MeetingEvents {
    public static final String MEETING_ID = "meetingId";
    public static final LocalDateTime DATE_TIME = LocalDateTime.parse("2020-11-18T15:00");
    public static final String ORGANIZER_ID = "organizerId";

    static MeetingPlanned meetingPlanned() {
        return new MeetingPlanned(MEETING_ID, DATE_TIME, ORGANIZER_ID);
    }

    static MeetingCancelled meetingCancelled() {
        return new MeetingCancelled(MEETING_ID);
    }
}
