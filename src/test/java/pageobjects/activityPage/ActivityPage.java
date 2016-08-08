package pageobjects.activityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.Page;

/**
 * Created by philipsushkov on 2016-08-07.
 */
public class ActivityPage extends Page {

    private final By pageTitle = By.cssSelector(".note-manager-header .page-title");

    public ActivityPage(WebDriver driver) {
        super(driver);
    }

    public String getActivityPageTitle() {

        return findElement(pageTitle).getText();
    }
}

