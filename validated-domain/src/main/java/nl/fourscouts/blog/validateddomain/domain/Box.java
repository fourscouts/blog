package nl.fourscouts.blog.validateddomain.domain;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static lombok.AccessLevel.PRIVATE;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor(access = PRIVATE)
public class Box {
	@AggregateIdentifier
	private String boxId;

	private int itemCount;
	private int size;

	@CommandHandler
	public Box(BuyBox command) {
		apply(new BoxBought(command.getBoxId(), command.getSize()));
	}

	@EventHandler
	public void onBought(BoxBought event) {
		boxId = event.getBoxId();

		itemCount = 0;
		size = event.getSize();
	}

	@CommandHandler
	public void addItems(AddItems command) {
		int count = command.getCount();
		int roomAvailable = size - (count + itemCount);

		if (roomAvailable < 0) {
			throw new NotEnoughRoomAvailable();
		} else {
			apply(new ItemsAdded(boxId, count, roomAvailable));
		}
	}

	@EventHandler
	public void onItemsAdded(ItemsAdded event) {
		itemCount += event.getCount();
	}
}
