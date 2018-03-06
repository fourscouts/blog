package nl.fourscouts.blog.secureddomain.integration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", glue = {"cucumber.api.spring", "nl.fourscouts.blog.secureddomain.integration"})
public class IntegrationTest {}
