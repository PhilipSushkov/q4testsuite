package pageobjects.user.noteDetailsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.user.activityPage.ActivityPage;

/**
 * Created by patrickp on 2016-08-12.
 */
public class NoteDetailsPage extends ActivityPage {
    private final By noteDetails = By.cssSelector(".preview-note-view .preview-note-view-section");
    private final By commentDetails = By.cssSelector(".note-detail-header .note-detail-header-inner");
    private final By linkedToDetails = By.cssSelector(".preview-note-view .note-links .x-dataview-item");
    private final By activityHeader = By.cssSelector(".note-detail-header .note-information h1");

    public NoteDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getNoteBody() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(noteDetails));
        return findElement(noteDetails).getText();
    }

    public String getCommentText() {
        waitForLoadingScreen();
        return findElement(commentDetails).getText();
    }

    public String getLinkedToText() {
        return findElement(linkedToDetails).getText();
    }

    public String getActivityHeader() {
        return findElement(activityHeader).getText();
    }
}
