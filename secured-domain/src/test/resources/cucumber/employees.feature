@txn
Feature: employees
	Scenario: Manager can raise salary
		Given employee E with salary 2450
		And the user is a manager
		When the user raises E's salary with 200
		Then the salary of employee E is 2650

	Scenario: Employee can't raise salary
		Given employee E with salary 2000
		And the user is an employee
		When the user raises E's salary with 1000
		Then the salary of employee E is 2000
