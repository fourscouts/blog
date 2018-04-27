package nl.fourscouts.blog.validateddomain.integration;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.fourscouts.blog.validateddomain.domain.AddItems;
import nl.fourscouts.blog.validateddomain.domain.BuyBox;
import nl.fourscouts.blog.validateddomain.projections.Box;
import nl.fourscouts.blog.validateddomain.projections.BoxRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class BoxSteps extends IntegrationSteps {
	@Autowired
	private BoxRepository boxRepository;

	@Given("box ([A-Z]) with size (\\d+)")
	public void boxWithSize(String boxId, int size) {
		commandGateway.sendAndWait(new BuyBox(boxId, size));
	}

	@When("(\\d+) items? (?:is|are) added to box ([A-Z])")
	public void itemsAreAdded(int count, String boxId) {
		sendCommand(new AddItems(boxId, count));
	}

	@Then("box ([A-Z]) has room left for (\\d+) more items?")
	public void checkAvailableRoom(String boxId, int availableRoom) {
		assertThat(boxRepository.findById(boxId)).map(Box::getAvailableRoom).contains(availableRoom);
	}
}
