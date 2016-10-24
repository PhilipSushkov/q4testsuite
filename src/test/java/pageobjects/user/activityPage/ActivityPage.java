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

    private final By loadingSpinner = By.className("outer-spinner-container");
    private final By notesSection = By.cssSelector(".note-manager-list .note-item.x-dataview-item");
    private final By loadMoreButton = By.cssSelector(".load-more .x-button-icon");
    private final By firstNoteInList = By.cssSelector(".note-manager-list .note-item.x-dataview-item");
    private final By newActivityIcon = By.xpath("/html/body/div/div/div/div/div/div[2]/div/div/div/div[2]/div[3]/div[1]/div[2]/div/div/div[2]/div/div[1]/div/div/div/div[4]");
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
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        return findElement(notesSection).getText();
    }

    public NoteDetailsPage selectFirstNoteInList() {
        findElement(firstNoteInList).click();

        return new NoteDetailsPage(getDriver());
    }

    public LogActivityModal logNote() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
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

