package nl.fourscouts.blog.domainownership.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static nl.fourscouts.blog.domainownership.domain.MeetingEvents.*;
import static nl.fourscouts.blog.domainownership.domain.UserIdDispatcher.USER_ID;

class MeetingTest {
    private AggregateTestFixture<Meeting> fixture;

    @BeforeEach
    void createFixture() {
        fixture = new AggregateTestFixture<>(Meeting.class);
    }

    @Test
    void shouldPlan() {
        fixture
                .givenNoPriorActivity()
                .when(new PlanMeeting(MEETING_ID, DATE_TIME), Collections.singletonMap(USER_ID, ORGANIZER_ID))
                .expectEvents(meetingPlanned());
    }

    @Test
    void shouldNotPlanWithoutOrganizer() {
        fixture
                .givenNoPriorActivity()
                .when(new PlanMeeting(MEETING_ID, DATE_TIME))
                .expectNoEvents();
    }

    @Test
    void shouldCancel() {
        fixture
                .given(meetingPlanned())
                .when(new CancelMeeting(MEETING_ID), Collections.singletonMap(USER_ID, ORGANIZER_ID))
                .expectEvents(meetingCancelled());
    }

    @Test
    void shouldAcceptCanccellationByUnknownUser() {
        fixture
                .given(meetingPlanned())
                .when(new CancelMeeting(MEETING_ID), Collections.singletonMap(USER_ID, "unknown"))
                .expectNoEvents();
    }
}
