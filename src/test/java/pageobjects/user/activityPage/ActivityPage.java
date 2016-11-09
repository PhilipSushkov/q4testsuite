package pageobjects.user.activityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.logActivity.LogActivityModal;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;

/**
 * Created by philipsushkov on 2016-08-07.
 */
public class ActivityPage extends AbstractPageObject {

    private final By notesSection = By.cssSelector(".note-manager-list .note-item.x-dataview-item");
    private final By firstNoteInList = By.cssSelector(".note-manager-list .note-item .column.title");
    private final By newActivityIcon = By.cssSelector(".q4-hero-banner .action-button.square");
    private final By activitySearchField = By.cssSelector(".toolbar-panel .search .x-field-input .x-input-el");
    private final By emptyResults = By.cssSelector(".note-manager-list .x-dataview-emptytext");
    private final By notesCount = By.xpath("//*[@class=\"counter\"][1]");
    private final By callCount = By.xpath("(//*[@class=\"counter\"])[2]");
    private final By emailCount = By.xpath("(//*[@class=\"counter\"])[3]");
    private final By meetingCount = By.xpath("(//*[@class=\"counter\"])[4]");

    public ActivityPage(WebDriver driver) {
        super(driver);
    }

    public String getNewNote() {
        // Waits for the load more button to appear at the bottom of the page.
        waitForLoadingScreen();
        return findElement(notesSection).getText();
    }

    public NoteDetailsPage selectFirstNoteInList() {
        findElement(firstNoteInList).click();

        return new NoteDetailsPage(getDriver());
    }

    public LogActivityModal logNote() {
        waitForLoadingScreen();
        findElement(newActivityIcon).click();

        return new LogActivityModal(getDriver());
    }

    public ActivityPage searchForNote(String note) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(activitySearchField));
        findElement(activitySearchField).click();
        findElement(activitySearchField).sendKeys(note);
        findElement(activitySearchField).sendKeys(Keys.RETURN);
        waitForLoadingScreen();

        return this;
    }

    public String getNoNote() {
        return findElement(emptyResults).getText();
    }

    public String getSearchResults() {
        return findElement(notesSection).getText();
    }

    public int getNoteCount() {
        pause(1000L);
        return Integer.parseInt(findElement(notesCount).getText());
    }

    public int getCallCount() {
        pause(1000L);
        return Integer.parseInt(findElement(callCount).getText());
    }

    public int getEmailCount() {
        pause(1000L);
        return Integer.parseInt(findElement(emailCount).getText());
    }

    public int getMeetingCount() {
        pause(1000L);
        return Integer.parseInt(findElement(meetingCount).getText());
    }
}

