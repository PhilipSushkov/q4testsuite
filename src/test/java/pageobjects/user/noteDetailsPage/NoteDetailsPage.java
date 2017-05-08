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
    private final By activityTitle = By.xpath("//div[contains(@class,'note-detail-header-inner')]//div[contains(@class,'note-information')][2]//h1");
    private final By linkedToDetails = By.cssSelector(".preview-note-view .note-links .x-dataview-item");
    private final By activityHeader = By.cssSelector(".note-detail-header .note-information h1");
    private final By detailsHeader = By.xpath("//h3[contains(string(),'Details')]");
    private final By detailsDate = By.cssSelector(".preview-note-view h4 + div");

    public NoteDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean detailsPageExists(){
        waitForLoadingScreen();

        try{
            findElement(detailsHeader);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    public String getNoteBody() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(noteDetails));
        return findElement(noteDetails).getText();
    }

    public String getActivityTitle(){
        waitForLoadingScreen();
        return findElement(activityTitle).getText();
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

    public String getDetailsDate(){
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(noteDetails));
        return findElement(detailsDate).getText();
    }
}
