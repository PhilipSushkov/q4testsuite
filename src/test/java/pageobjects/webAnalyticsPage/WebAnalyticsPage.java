package pageobjects.webAnalyticsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-16.
 */
public class WebAnalyticsPage extends AbstractPageObject{
    private final By pageTitle = By.cssSelector(".analytics-page");

    public WebAnalyticsPage(WebDriver driver) {
        super(driver);
    }

    public String getWebAnalyticsPageTitle() {
        pause(3000L);
        waitForElementToAppear(pageTitle);
        return findElement(pageTitle).getText();
    }
}
