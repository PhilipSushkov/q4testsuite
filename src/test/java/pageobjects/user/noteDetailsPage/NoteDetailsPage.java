package pageobjects.user.noteDetailsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.user.activityPage.ActivityPage;

/**
 * Created by patrickp on 2016-08-12.
 */
public class NoteDetailsPage extends ActivityPage {
    private final By noteDetails = By.xpath("//div[contains(@class, 'x-container x-unsized x-size-monitored x-paint-monitored notes')]/div[contains(@class, 'x-inner')]/div[contains(@class, 'x-innerhtml')]/div[contains(@class, 'q4-fade-in')]");
    private final By commentDetails = By.cssSelector(".activity-header .detail-header .detail-info .title");
    private final By activityTitle = By.xpath("//div[contains(@class, 'detail-info')]/h2[contains(@class, 'title')]");
    private final By linkedToDetails = By.xpath("//div[contains(@class, 'x-container x-unsized activity-attendees-list x-dataview x-paint-monitored')]//div[contains(@class, 'column flex name')]");
    private final By activityHeader = By.cssSelector(".note-detail-header .note-information h1");
    private final By detailsHeader = By.xpath("//div[contains(@class, 'x-container x-tabbar x-dock-item x-docked-top x-stretched')]");
    private final By activityDetails = By.cssSelector(".preview-note-view p");
    private final By venueDetails = By.xpath("//div[contains(@class,'x-innerhtml')][h4[contains(text(),'Venue')]]");
    private final By locationDetails = By.xpath("//div[contains(@class, 'x-innerhtml')]/ul[contains(@class, 'bordered-list')]/li[2]");
    private final By attendeeDetails = By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'details')]");
    private final By dateDetails = By.xpath("//div[contains(@class, 'x-innerhtml')]/ul[contains(@class, 'bordered-list')]/li[1]");
    private final By contactAttendees = By.xpath("//div[contains(@class, 'x-inner x-data-item-inner toolbar-panel-inner attendees-toolbar-inner')]/div[contains(@class, 'x-container range-tabs dark-tabs x-stretched')]/div[contains(@class, 'x-inner range-tabs-inner x-align-center x-horizontal x-pack-start x-layout-box')]/div[contains(@class, 'x-button x-button-no-icon x-layout-box-item x-stretched')]/*[contains(text(), 'Contact')]");
    private final By institutionTab = By.xpath("//div[contains(@class,'x-button')][span[contains(text(),'Institution')][span[contains(@class,'counter')]]]");
    private final By fundTab = By.xpath("//div[contains(@class,'x-button')][span[contains(text(),'Fund')][span[contains(@class,'counter')]]]");
    private final By contactTab = By.xpath("//div[contains(@class,'x-button')][span[contains(text(),'Contact')][span[contains(@class,'counter')]]]");

    // Suggest an Edit Note Menu
    private final By suggestEditMenuDropdownButton = By.cssSelector("#ext-element-661");
    private final By suggestEditMenuButton = By.cssSelector(".utility-menu-items > .x-inner");
    private final By suggestEditTextBox = By.name("message");
    private final By suggestPostEditButton = By.cssSelector(".q4-form .form-button.yellow");
    private final By suggestCancelEditButton = By.cssSelector(".q4-form .form-button.no-background");

    // Edit Note
    private final By editButton = By.cssSelector("#ext-button-41");

    // Itinerary Section
    private final By itineraryTitle = By.xpath("//div[contains(@class,'itinerary-list-item')]/div[contains(@class, 'general-information')]/div[contains(@class, 'title')]");
    private final By itineraryDate = By.xpath("//div[contains(@class,'itinerary-list-item')]/div[contains(@class, 'general-information')]/div[contains(@class, 'date')]");
    private final By itineraryVenue = By.xpath("//div[contains(@class,'itinerary-list-item')]/div[contains(@class, 'general-information')]/div[contains(@class, 'venue')]");
    private final By itineraryParticipants = By.xpath("//div[contains(@class,'itinerary-list-item')]/div[contains(@class, 'general-information')]/div[contains(@class, 'participants')]");
    private final By itineraryTime = By.xpath("//div[contains(@class,'itinerary-list-item')]/div[contains(@class, 'general-information')]/div[contains(@class, 'time')]");
    private final By itineraryAttendees = By.xpath("//div[contains(@class,'itinerary-list-item')]/div[contains(@class,'links')]");
    private final By detailsDate = By.cssSelector(".preview-note-view h4 + div");
    private final By detailsTag = By.cssSelector(".tags-view span.tag");
    private final By addTagButton = By.cssSelector(".tags-view .tag.add-button");
    private final By addTagField = By.xpath("//div[@class='x-unsized x-field-input x-has-width']//input[@class='x-input-el x-form-field x-input-text']");
    private final By detailsLocation = By.xpath("//div[contains(@class, 'x-innerhtml')]/ul/li[2]");

    public NoteDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean detailsPageExists(){
        waitForLoadingScreen();

        try{
            waitForElement(detailsHeader);
            findElement(detailsHeader);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    public NoteDetailsPage getSuggestEditTextBox(){

        wait.until(ExpectedConditions.elementToBeClickable(suggestEditMenuDropdownButton));
        findElement(suggestEditMenuDropdownButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(suggestEditMenuButton));
        findElement(suggestEditMenuButton).click();

        return this;
    }

    public NoteDetailsPage postASuggestedEdit (String comment){

        wait.until(ExpectedConditions.elementToBeClickable(suggestEditTextBox));
        findElement(suggestEditTextBox).sendKeys(comment);
        wait.until(ExpectedConditions.elementToBeClickable(suggestPostEditButton));
        findElement(suggestPostEditButton).click();

        return this;
    }

    public NoteDetailsPage cancelASuggestedEdit (String comment){

        wait.until(ExpectedConditions.elementToBeClickable(suggestEditTextBox));
        findElement(suggestEditTextBox).clear();
        findElement(suggestEditTextBox).sendKeys(comment);
        wait.until(ExpectedConditions.elementToBeClickable(suggestCancelEditButton));
        findElement(suggestCancelEditButton).click();

        return this;
    }

    public NoteDetailsPage goToEditPage(){

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        findElement(editButton).click();
        return this;
    }

    public String returnAttendeesList(){
        waitForElement(linkedToDetails);
        return findVisibleElement(linkedToDetails).getText();
    }

    public NoteDetailsPage clickInstitutionTab(){
        wait.until(ExpectedConditions.elementToBeClickable(institutionTab));
        findVisibleElement(institutionTab).click();
        waitForLoadingScreen();
        return this;
    }
    public NoteDetailsPage clickFundTab(){
        wait.until(ExpectedConditions.elementToBeClickable(fundTab));
        findVisibleElement(fundTab).click();
        waitForLoadingScreen();
        return this;
    }
    public NoteDetailsPage clickContactTab(){
        wait.until(ExpectedConditions.elementToBeClickable(contactTab));
        findVisibleElement(contactTab).click();
        waitForLoadingScreen();
        return this;
    }


    public String getActivityHeader() {
        return findElement(activityHeader).getText();
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

    public String getActivityDetails(){
        try {
            waitForLoadingScreen();
            return findElement(activityDetails).getText();
        }
        catch(ElementNotFoundException e)
        {
            return "";
        }
    }

    public String getDate(){
        waitForLoadingScreen();
        waitForElement(dateDetails);
        return findElement(dateDetails).getText();
    }

    public String getVenueDetails(){
        waitForLoadingScreen();
        waitForElement(venueDetails);
        return findElement(venueDetails).getText();
    }

    public String getLocation(){
        waitForLoadingScreen();
        waitForElement(locationDetails);
        waitForTextToChange(locationDetails, "...");
        return findElement(locationDetails).getText();
    }

    public String getCommentText() {
        waitForLoadingScreen();
        waitForElement(commentDetails);
        return findElement(commentDetails).getText();
    }

    // index ensures the correct participant is selected
    public String getParticipants(){
        waitForLoadingScreen();
        waitForElement(locationDetails);
        return findElement(locationDetails).getText();
    }

    public String getAttendees(){
        waitForLoadingScreen();
        waitForElement(attendeeDetails);
        return findElement(attendeeDetails).getText();
    }

    public String getItineraryTitle(){
        waitForLoadingScreen();
        waitForElement(itineraryTitle);
        return findElement(itineraryTitle).getText();
    }

    public String getItineraryDate(){
        waitForLoadingScreen();
        waitForElement(itineraryDate);
        return findElement(itineraryDate).getText();
    }

    public String getItineraryTime(){
        waitForLoadingScreen();
        waitForElement(itineraryTime);
        return findElement(itineraryTime).getText();
    }

    public String getItineraryVenue(){
        waitForLoadingScreen();
        waitForElement(itineraryVenue);
        return findElement(itineraryVenue).getText();
    }

    public String getItineraryParticipants(){
        waitForLoadingScreen();
        waitForElement(itineraryParticipants);
        return findElement(itineraryParticipants).getText();
    }

    public String getItineraryAttendees(){
        waitForLoadingScreen();
        waitForElement(itineraryAttendees);
        return findElement(itineraryAttendees).getText();
    }

    public String getDetailsDate(){
        waitForLoadingScreen();
        waitForElementToAppear(dateDetails);
        return findElement(dateDetails).getText();
    }

    public String getDetailsTag(){
        //Get tag from the details page
        waitForLoadingScreen();
        return waitForElementToBeClickable(detailsTag).getText();
    }

    public NoteDetailsPage addNewTag(String newTag){
        //Add new tag from details page
        waitForElementToBeClickable(addTagButton).click();
        waitForElementToBeClickable(addTagField).click();
        findElement(addTagField).sendKeys(newTag);
        findElement(addTagField).sendKeys(Keys.ENTER);
        findElement(addTagField).sendKeys(Keys.ESCAPE);

        return this;
    }

    public String getDetailsLocation(){
        //Gets the location from details page - detailsLocation will return all content inside details page, substringBetween selects the location
        waitForLoadingScreen();
        waitForElement(detailsLocation);
        return findElement(detailsLocation).getText();
    }
}
