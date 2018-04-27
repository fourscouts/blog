@txn
Feature: boxes
	Scenario: Items can be added as long as there is room available
		Given box B with size 3
		When 1 item is added to box B
		Then box B has room left for 2 more items

	Scenario: Items can't be added when there is no room available
		Given box B with size 1
		When 2 items are added to box B
		Then validation fails
		And box B has room left for 1 more item
