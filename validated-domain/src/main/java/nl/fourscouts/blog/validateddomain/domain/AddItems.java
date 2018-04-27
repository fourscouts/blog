package nl.fourscouts.blog.validateddomain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
@RoomAvailable
public class AddItems {
	@TargetAggregateIdentifier
	private String boxId;

	private int count;
}
