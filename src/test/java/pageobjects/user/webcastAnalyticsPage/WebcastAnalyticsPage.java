package pageobjects.user.webcastAnalyticsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-16.
 */
public class WebcastAnalyticsPage extends AbstractPageObject {

    private final By webcastAnalyticsHeader = By.xpath("//h1[contains(string(),'Webcast Analytics')]");

    private final By firstWebcastOnTheList = By.cssSelector(".row");
    private final By firstWebcastOnTheListTitle = By.cssSelector("div > div.column.flex > div > div.title");
    private final By firstWebcastOnTheListRegistrants = By.cssSelector("div > div:nth-child(2) > span.counter");
    private final By firstWebcastOnTheListLiveAttendees = By.cssSelector("div > div:nth-child(3) > span.counter");
    private final By firstWebcastOnTheListOnDemandAttendees = By.cssSelector("div > div:nth-child(4) > span.counter");
    private final By firstWebcastOnTheListTotalAttendees = By.cssSelector("div > div:nth-child(5) > span.counter");

    public WebcastAnalyticsPage(WebDriver driver) {
        super(driver);
    }

    public WebcastAnalyticsDetailsPage selectFirstWebcast(){
        waitForLoadingScreen();
        findElement(firstWebcastOnTheList).click();

        return new WebcastAnalyticsDetailsPage(getDriver());
    }

    public String getWebcastTitle(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListTitle).getText();
    }

    public String getWebcastRegistrants(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListRegistrants).getText();
    }

    public String getWebcastLiveAttendees(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListLiveAttendees).getText();
    }

    public String getWebcastOnDemandAttendees(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListOnDemandAttendees).getText();
    }

    public String getWebcastTotalAttendees(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListTotalAttendees).getText();
    }

}
