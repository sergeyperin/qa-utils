package pages

import geb.Page
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions

class LandingPage extends Page {
    static atCheckWaiting = 10
    static at = {
        (title == "Buy cheap air tickets online at anywayanyday - Anywayanyday")
    }
    static url = "https://www.anywayanyday.com/en/"

    static content = {
        departureCity(wait: true, required: true) {
            $(By.xpath("//label[text() = 'DEPARTURE FROM']/following-sibling::input[contains(@class, 'aviaInput')]"))
        }
        arriveInCity(wait: true, required: true) {
            $(By.xpath("//div[contains(@class, 'arrivalAirport')]//input"))
        }
        departureDate(wait: true, required: true) { $("div", text: "Select a date") }
        searchButton(wait: 7, required: true, to: SearchResultPage) { $("a", text: "SEARCH") }
        hotelsLink(wait: true, required: false) { $("a").has("span", text: "Hotels") }
        trainTicketsLink(wait: true, required: false) { $("a").has("span", text: "Train tickets") }
        carHireLink(wait: true, required: false) { $("a").has("span", text: "Car hire") }
        selectedDate(wait: true, required: false) { year, month, day ->
            $(By.xpath("//div[contains(@class, 'aviaForm')]//div[contains(@class, 'formWrapper')]//div[contains(@class, ' awadCalendar-month first')]/table/tbody//td[@data-year='${year}' and @data-month='${month}' and @data-day='${day}']"))
        }
    }

    void selectTripDate(year, month, day) {
        waitFor(5, 0.5) {
            selectedDate(year, month, day).displayed
        }
        selectedDate(year, month, day).click()
    }
}

