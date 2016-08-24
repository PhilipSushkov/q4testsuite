package pageobjects.webcastAnalyticsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-16.
 */
public class WebcastAnalyticsPage extends AbstractPageObject {

    private final By pageTitle = By.cssSelector(".page-header");

    public WebcastAnalyticsPage(WebDriver driver) {
        super(driver);
    }

    public String getWecastAnalyticsPageTitle() {
        wait.until(ExpectedConditions.elementToBeClickable(pageTitle));
        return findElement(pageTitle).getText();
    }
}
