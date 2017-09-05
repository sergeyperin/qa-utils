package pages

import geb.Page
import org.openqa.selenium.By

class SearchResultPage extends Page {
    static atCheckWaiting = 10
    static at = {
        (title == "Flight selection - Anywayanyday")
    }
    static url = "/en/avia/offers/flightDate"

    static content = {
        tripDateTimestamp(wait: true, required: false) {
            // not implemented for demo
        }
        directFlight(wait: true, required: true) {
            $(By.xpath("//span[contains(normalize-space(), 'No connections')]")).parent("div").next("div").children("div")[1]
        }
        connectionFlight(wait: 30, required: true) {
            $(By.xpath("//span[contains(normalize-space(), 'One connection')]")).parent("div").next("div").children("div")[1]
        }
    }
}
