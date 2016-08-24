package pageobjects.eventsTranscriptsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-16.
 */
public class EventsTranscriptsPage extends AbstractPageObject {

    private final By pageTitle = By.cssSelector(".page-header");

    public EventsTranscriptsPage(WebDriver driver) {
        super(driver);
    }

    public String getEventsPageTitle() {
        return findElement(pageTitle).getText();
    }
}
