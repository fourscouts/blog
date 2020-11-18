package nl.fourscouts.blog.domainownership.api;

import nl.fourscouts.blog.domainownership.domain.CancelMeeting;
import nl.fourscouts.blog.domainownership.domain.PlanMeeting;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static nl.fourscouts.blog.domainownership.domain.MeetingEvents.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MeetingController.class)
class MeetingControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IdentifierFactory identifierFactory;
    @MockBean
    private CommandGateway commandGateway;

    @Test
    @WithMockUser
    void shouldPlanMeeting() throws Exception {
        when(identifierFactory.generateIdentifier()).thenReturn(MEETING_ID);

        mvc
            .perform(post("/meetings")
                    .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"dateTime\": \"2020-11-18T15:00\" }")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("meetingId").value(MEETING_ID));

        verify(commandGateway).sendAndWait(new PlanMeeting(MEETING_ID, DATE_TIME));
    }

    @Test
    @WithMockUser
    void shouldCancelMeeting() throws Exception {
        mvc
            .perform(delete("/meetings/{meetingId}", MEETING_ID).with(csrf()))
            .andExpect(status().isOk());

        verify(commandGateway).sendAndWait(new CancelMeeting(MEETING_ID));
    }
}
