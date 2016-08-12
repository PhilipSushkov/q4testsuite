package pageobjects.noteDetailsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.activityPage.ActivityPage;

/**
 * Created by patrickp on 2016-08-12.
 */
public class NoteDetailsPage extends ActivityPage {
    private final By noteDetails = By.cssSelector(".preview-note-view .activity-detail-body");
    private final By commentDetails = By.cssSelector(".note-detail-header .note-detail-header-inner");
    private final By linkedToDetails = By.cssSelector(".preview-note-view .links .link");

    public NoteDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getNoteBody() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(noteDetails));
        return findElement(noteDetails).getText();
    }

    public String getCommentText() {
        return findElement(commentDetails).getText();
    }

    public String getLinkedToText() {
        return findElement(linkedToDetails).getText();
    }
}
