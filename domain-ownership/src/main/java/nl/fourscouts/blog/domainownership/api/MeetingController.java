package nl.fourscouts.blog.domainownership.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nl.fourscouts.blog.domainownership.domain.AccessDenied;
import nl.fourscouts.blog.domainownership.domain.CancelMeeting;
import nl.fourscouts.blog.domainownership.domain.PlanMeeting;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@AllArgsConstructor
@RequestMapping("meetings")
public class MeetingController {
    private IdentifierFactory identifierFactory;
    private CommandGateway commandGateway;

    @PostMapping
    public MeetingResponse planMeeting(@RequestBody MeetingRequest request) {
        String meetingId = identifierFactory.generateIdentifier();

        commandGateway.sendAndWait(new PlanMeeting(meetingId, request.dateTime));

        return new MeetingResponse(meetingId);
    }

    @DeleteMapping("{meetingId}")
    public void cancelMeeting(@PathVariable String meetingId) {
        commandGateway.sendAndWait(new CancelMeeting(meetingId));
    }

    @ExceptionHandler
    @ResponseStatus(FORBIDDEN)
    public void onAccessDenied(AccessDenied exception) {
        exception.printStackTrace();
    }

    @Data
    static class MeetingRequest {
        private LocalDateTime dateTime;
    }

    @Value
    static class MeetingResponse {
        String meetingId;
    }
}
