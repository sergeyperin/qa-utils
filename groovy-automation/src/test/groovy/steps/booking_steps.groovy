import cucumber.api.PendingException
import io.jdev.geb.cucumber.core.en.FieldFinderEN

import static cucumber.api.groovy.EN.*

/**
 * Alternative to use to go to LandingPage if you don't want to use built-in /I go to the landing page/
 */
Given(~/^I open Anywayanyday booking portal$/) {
    to pages.LandingPage
}

When(~/^I select "([^"]*)" year, "([^"]*)" month, "([^"]*)" day$/) { String year, String month, String day ->
    page.selectTripDate(year, month, day);
}

When(~/^I enter "([^"]*)" into "([^"]*)"$/) { String value, String fieldName ->
    def field = new FieldFinderEN().findField(fieldName, browser.page)
    field.value(value);
}