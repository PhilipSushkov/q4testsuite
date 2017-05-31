package pageobjects.user.noteDetailsPage;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
    private final By activityDetails = By.cssSelector(".preview-note-view p");
    private final By venueDetails = By.xpath("//div[contains(@class,'x-innerhtml')][h4[contains(text(),'Venue')]]");
    private final By locationDetails = By.xpath("//div[contains(@class, 'x-innerhtml')][h4[contains(text(), 'Location')]]");
    private final By attendeeDetails = By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'details')]");
    private final By dateDetails = By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')][h4[contains(text(), 'Date')]]/div");

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
    private final By addTagField = By.cssSelector(".tags-modal-list .tag-field input");
    private final By detailsLocation = By.xpath("//div[contains(@class, 'x-innerhtml')][h4[contains(text(), 'Location')]]");

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

    public String getLinkedToText() {
        return findElement(linkedToDetails).getText();
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
        return findElement(locationDetails).getText();
    }

    public String getVenueDetails(){
        waitForLoadingScreen();
        return findElement(venueDetails).getText();
    }

    public String getLocation(){
        waitForLoadingScreen();
        return findElement(locationDetails).getText();
    }

    public String getCommentText() {
        waitForLoadingScreen();
        return findElement(commentDetails).getText();
    }

    // index ensures the correct participant is selected
    public String getParticipants(){
        waitForLoadingScreen();
        return findElement(locationDetails).getText();
    }

    public String getAttendees(){
        waitForLoadingScreen();
        return findElement(attendeeDetails).getText();
    }

    public String getItineraryTitle(){
        waitForLoadingScreen();
        return findElement(itineraryTitle).getText();
    }

    public String getItineraryDate(){
        waitForLoadingScreen();
        return findElement(itineraryDate).getText();
    }

    public String getItineraryTime(){
        waitForLoadingScreen();
        return findElement(itineraryTime).getText();
    }

    public String getItineraryVenue(){
        waitForLoadingScreen();
        return findElement(itineraryVenue).getText();
    }

    public String getItineraryParticipants(){
        waitForLoadingScreen();
        return findElement(itineraryParticipants).getText();
    }

    public String getItineraryAttendees(){
        waitForLoadingScreen();
        return findElement(itineraryAttendees).getText();
    }

    public String getDetailsDate(){
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(noteDetails));
        return findElement(detailsDate).getText();
    }

    public String getDetailsTag(){
        //Get tag from the details page
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(noteDetails));
        return findElement(detailsTag).getText();
    }

    public NoteDetailsPage addNewTag(String newTag){
        //Add new tag from details page
        waitForLoadingScreen();
        findElement(addTagButton).click();
        waitForElementToAppear(addTagField);
        findElement(addTagField).click();
        findElement(addTagField).sendKeys(newTag);
        findElement(addTagField).sendKeys(Keys.ENTER);

        return this;
    }

    public String getDetailsLocation(){
        //Gets the location from details page - detailsLocation will return all content inside details page, substringBetween selects the location
        waitForLoadingScreen();
        return StringUtils.substringBetween(findElement(detailsLocation).getText(),"LOCATION\n","\nCORPORATE");
    }
}
