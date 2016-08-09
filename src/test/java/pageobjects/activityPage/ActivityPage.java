package pageobjects.activityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.Page;

/**
 * Created by philipsushkov on 2016-08-07.
 */
public class ActivityPage extends Page {

    private final By pageTitle = By.cssSelector(".note-manager-header .page-title");
    private final By notesSection = By.id("ext-note-list-1");
    private final By loadMoreButton = By.cssSelector(".load-more .x-button-icon");

    public ActivityPage(WebDriver driver) {
        super(driver);
    }

    public String getActivityPageTitle() {

        return findElement(pageTitle).getText();
    }

    public String getNewNote() {
        // Waits for the load more button to appear at the bottom of the page
        waitForElementToAppear(loadMoreButton);
        return findElement(notesSection).getText();
    }
}
