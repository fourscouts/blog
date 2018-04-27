package nl.fourscouts.blog.validateddomain.projections;

import nl.fourscouts.blog.validateddomain.domain.BoxBought;
import nl.fourscouts.blog.validateddomain.domain.ItemsAdded;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoxProjections {
	private BoxRepository boxRepository;

	@Autowired
	public BoxProjections(BoxRepository boxRepository) {
		this.boxRepository = boxRepository;
	}

	@EventHandler
	public void onBoxBought(BoxBought event) {
		boxRepository.save(new Box(event.getBoxId(), event.getSize()));
	}

	@EventHandler
	public void onItemsAdded(ItemsAdded event) {
		boxRepository.update(event.getBoxId(), event.getAvailableRoom());
	}
}
