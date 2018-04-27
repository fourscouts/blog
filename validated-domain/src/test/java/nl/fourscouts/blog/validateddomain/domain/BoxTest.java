package nl.fourscouts.blog.validateddomain.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static nl.fourscouts.blog.validateddomain.domain.BoxEvents.BOX_ID;
import static nl.fourscouts.blog.validateddomain.domain.BoxEvents.SIZE;
import static nl.fourscouts.blog.validateddomain.domain.BoxEvents.boxBought;
import static nl.fourscouts.blog.validateddomain.domain.BoxEvents.itemsAdded;
import static nl.fourscouts.blog.validateddomain.domain.BoxEvents.itemsAddedToNewBox;

public class BoxTest {
	private FixtureConfiguration<Box> fixture;

	@Before
	public void createFixture() {
		fixture = new AggregateTestFixture<>(Box.class);
	}

	@Test
	public void shouldBeBuyable() {
		fixture
			.givenNoPriorActivity()
			.when(new BuyBox(BOX_ID, SIZE))
			.expectEvents(boxBought());
	}

	@Test
	public void shouldAddItemsToNewBox() {
		fixture
			.given(boxBought())
			.when(new AddItems(BOX_ID, 2))
			.expectEvents(itemsAddedToNewBox(2));
	}

	@Test
	public void shouldAddItemsToBoxThatAlreadyContainsItems() {
		fixture
			.given(boxBought(), itemsAddedToNewBox(2))
			.when(new AddItems(BOX_ID, 1))
			.expectEvents(itemsAdded(1, 0));
	}

	@Test
	public void shouldNotAddItemsToBoxWithNotEnoughRoomAvailable() {
		fixture
			.given(boxBought(), itemsAddedToNewBox(2))
			.when(new AddItems(BOX_ID, 2))
			.expectException(NotEnoughRoomAvailable.class);
	}
}
