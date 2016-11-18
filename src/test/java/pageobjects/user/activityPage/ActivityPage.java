package pageobjects.user.activityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.logActivity.LogActivityModal;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;

import java.util.ArrayList;
import java.util.List;

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
    private final By filtersButton = By.xpath("//div[contains(@class,'rectangular')]//span[contains(text(),'Filters')]");
    private final By searchButton = By.cssSelector(".note-manager-filters .form-button.yellow");


    //This is actually the text beside the checkbox. Clicking the checkbox is proving to be difficult
    private final By noteFilterCheckbox = By.xpath("//div[contains(@class,'note-manager-filters-checkbox')]//span[contains(text(),'Note')]");
    private final By phoneFilterCheckbox = By.xpath("//div[contains(@class,'note-manager-filters-checkbox')]//span[contains(text(),'Phone')]");
    private final By emailFilterCheckbox = By.xpath("//div[contains(@class,'note-manager-filters-checkbox')]//span[contains(text(),'Email')]");
    private final By meetingFilterCheckbox = By.xpath("//div[contains(@class,'note-manager-filters-checkbox')]//span[contains(text(),'Meeting')]");
    private final By activityDataTable = By.cssSelector(".x-dataview-container");

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

    public ActivityPage clickFiltersButton(){
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(filtersButton));
        findElement(filtersButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        return this;
    }

    /**
     * FILTERS
     */


    public ActivityPage clickFilterCheckbox(FilterType filter){
        By selector = null;
        switch(filter){
            case EMAIL:
                selector=emailFilterCheckbox;
                break;
            case PHONE:
                selector=phoneFilterCheckbox;
                break;
            case NOTE:
                selector=noteFilterCheckbox;
                break;
            case MEETING:
                selector=meetingFilterCheckbox;
                break;
        }

        wait.until(ExpectedConditions.elementToBeClickable(selector));
        findElement(selector).click();
        return this;

    }

    public ActivityPage clickSearch(){
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        findElement(searchButton).click();
        waitForLoadingScreen();
        return this;
    }

    public boolean isFilteredCorrectly(FilterType filter){
         List<WebElement> rowList = findElements(By.xpath("//div[contains(@class,'x-dataview-container')]//div[contains(@class,'row')]//i"));
         ArrayList<WebElement> rowArrayList = new ArrayList<>(rowList);


        for (WebElement i : rowArrayList){
            if(!(i.getAttribute("class").equals(filter.iconClass()))){
                return false;
            }
        }

        return true;
    }

    /**
     *
     *
     */

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

