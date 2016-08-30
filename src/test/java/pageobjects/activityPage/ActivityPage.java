package pageobjects.activityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.logActivity.LogActivityModal;
import pageobjects.noteDetailsPage.NoteDetailsPage;

/**
 * Created by philipsushkov on 2016-08-07.
 */
public class ActivityPage extends AbstractPageObject {

    private final By pageTitle = By.cssSelector(".q4-hero-banner .page-title");
    private final By notesSection = By.id("ext-note-list-1");
    private final By loadMoreButton = By.cssSelector(".load-more .x-button-icon");
    private final By firstNoteInList = By.cssSelector(".note-manager-list .note-header");
    private final By newActivityIcon = By.cssSelector(".q4-hero-banner .action-button + .action-button");
    private final By activitySearchField = By.cssSelector(".toolbar-panel .search .x-field-input .x-input-el");
    private final By emptyResults = By.cssSelector(".note-manager-list .x-dataview-emptytext");

    public ActivityPage(WebDriver driver) {
        super(driver);
    }

    public String getActivityPageTitle() {

        return findElement(pageTitle).getText();
    }

    public String getNewNote() {
        // Waits for the load more button to appear at the bottom of the page.
        waitForElementToAppear(loadMoreButton);
        return findElement(notesSection).getText();
    }

    public NoteDetailsPage selectFirstNoteInList() {
        findElement(firstNoteInList).click();

        return new NoteDetailsPage(getDriver());
    }

    public LogActivityModal logNote() {
        pause(500L);
        wait.until(ExpectedConditions.elementToBeClickable(newActivityIcon));
        findElement(newActivityIcon).click();

        return new LogActivityModal(getDriver());
    }

    public ActivityPage searchForNote(String note) {
        pause(500L);
        wait.until(ExpectedConditions.elementToBeClickable(activitySearchField));
        findElement(activitySearchField).click();
        findElement(activitySearchField).sendKeys(note);
        findElement(activitySearchField).sendKeys(Keys.RETURN);
        pause(500L);

        return this;
    }

    public String getNoNote() {
        return findElement(emptyResults).getText();
    }

    public String getSearchResults() {
        return findElement(notesSection).getText();
    }
}

