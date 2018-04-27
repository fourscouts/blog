package nl.fourscouts.blog.validateddomain.projections;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.SimpleEventHandlerInvoker;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static nl.fourscouts.blog.validateddomain.domain.BoxEvents.BOX_ID;
import static nl.fourscouts.blog.validateddomain.domain.BoxEvents.SIZE;
import static nl.fourscouts.blog.validateddomain.domain.BoxEvents.boxBought;
import static nl.fourscouts.blog.validateddomain.domain.BoxEvents.itemsAddedToNewBox;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan
@RunWith(SpringRunner.class)
public class BoxProjectionsTest {
	@Autowired
	private BoxProjections projections;

	@Autowired
	private BoxRepository repository;

	private EventBus eventBus;

	private SubscribingEventProcessor eventProcessor;

	@Before
	public void createEventBus() {
		eventBus = new SimpleEventBus();

		eventProcessor = new SubscribingEventProcessor("listener", new SimpleEventHandlerInvoker(projections), eventBus);
		eventProcessor.start();
	}

	@After
	public void stopEventListener() {
		eventProcessor.shutDown();
	}

	@Test
	public void shouldSaveNewBox() {
		publish(boxBought());

		assertThat(repository.findById(BOX_ID)).map(Box::getAvailableRoom).contains(SIZE);
	}

	@Test
	public void shouldUpdateAvailableRoomWhenItemsAreAdded() {
		publish(boxBought(), itemsAddedToNewBox(2));

		assertThat(repository.findById(BOX_ID)).map(Box::getAvailableRoom).contains(1);
	}

	private void publish(Object... events) {
		Arrays.stream(events).forEach(event -> eventBus.publish(GenericEventMessage.asEventMessage(event)));
	}
}