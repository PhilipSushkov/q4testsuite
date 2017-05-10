package pageobjects.user.noteDetailsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.user.Calendar;
import pageobjects.user.activityPage.ActivityPage;
/**
 * Created by dannyl on 2017-05-05.
 */

// CHANGE ALL SELECTORS USING EXT ELEMENTS
public class editNoteDetailsPage extends ActivityPage {

    private final By titleField = By.name("title");
    private final By noteField = By.cssSelector("#ext-element-458_ifr");
    private final By saveButton = By.cssSelector(".note-detail-header .action-button.save");
    private final By cancelButton = By.cssSelector(".note-detail-header .action-button.cancel");

    // Type Menu Buttons
    private final By changeTypeToNoteButton = By.cssSelector("#ext-radiofield-1");
    private final By changeTypeToPhoneButton = By.cssSelector("#ext-radiofield-2");
    private final By changeTypeToEmailButton = By.cssSelector("#ext-radiofield-3");
    private final By changeTypeToMeetingButton = By.cssSelector("#ext-radiofield-4");
    private final By changeTypeToRoadshowButton = By.cssSelector("#ext-radiofield-5");

    // Text Field Option Buttons
    private final By toggleBoldTextButton = By.cssSelector("#mceu_14 > button");
    private final By toggleItalicsButton = By.cssSelector("#mceu_15 > button");
    private final By toggleUnderlineButton = By.cssSelector("#mceu_16 > button");
    private final By toggleLeftIndentButton = By.cssSelector("#mceu_17 > button");
    private final By toggleCenterTextButton = By.cssSelector("#mceu_18 > button");
    private final By toggleRightIndentButton = By.cssSelector("#mceu_19 > button");

    // Font Sizes
    private final By fontDropdownMenu = By.cssSelector("#mceu_13-open");
    private final By fontSize11 = By.cssSelector("#mceu_28-text");
    private final By fontSize13 = By.cssSelector("#mceu_29");
    private final By fontSize15 = By.cssSelector("#mceu_30");
    private final By fontSize18 = By.cssSelector("#mceu_31");
    private final By fontSize24 = By.cssSelector("#mceu_32");

    // Dropdown Menus
    private final By dateField = By.cssSelector("#ext-pickmeup-3");
    private final By nextMonthButton = By.cssSelector(".pickmeup .pmu-instance nav .pmu-next");
    private final By profileMenu = By.cssSelector("#ext-togglefield-4");
    private final By profileInstitutionButton = By.cssSelector(".note-link-field .entities .x-field-radio .x-form-label.institution");
    private final By profileFundButton = By.cssSelector(".note-link-field .entities .x-field-radio .x-form-label.fund");
    private final By profileContactButton = By.cssSelector(".note-link-field .entities .x-field-radio .x-form-label.contact");
    private final By profileSearchField = By.cssSelector(".note-link-field .search-field.x-field .x-form-field");
    private final By startTimeMenu = By.name("startTime");
    private final By endTimeMenu = By.name("endTime");
    private final By venueMenu = By.name("venue");
    private final By locationMenu = By.cssSelector("#ext-element-495");
    private final By corporateTextField = By.cssSelector(".note-participants-field .participants-field .x-form-field");
    private final By corporateAddButton = By.cssSelector(".edit-note-view .column-4 .note-participants-field .add-participant");

    // Itinerary Menu
    private final By itineraryDate = By.cssSelector("#ext-element-563");
    private final By itineraryDatePrev = By.cssSelector(".pickmeup .pmu-instance nav .pmu-prev");
    private final By itineraryDateNext = By.cssSelector("");
    private final By itineraryCurrentMonth = By.cssSelector(".pickmeup .pmu-instance:first-child:last-child .pmu-month");
    private final By itineraryCurrentDay = By.xpath("//div[contains(@class, 'pickmeup light-theme pmu-view-days')]/div[contains(@class, 'pmu-instance')]" +
            "/div[contains(@class, 'pmu-days')]/div[contains (@class, 'pmu-selected pmu-today pmu-button')])");
    private final By chosenDay = By.xpath("//div[contains(@class, 'pickmeup light-theme pmu-view-days')]/div[contains(@class, 'pmu-instance')]/div[contains(@class, 'pmu-days')]/*[contains (text(), '7')]");
    private final By itineraryShowButton = By.cssSelector(".note-itinerary .show-itinerary-button");
    private final By itineraryStartTimeMenu = By.cssSelector("#ext-element-569");
    private final By itineraryEndTimeMenu = By.cssSelector("#ext-element-574");
    private final By itineraryTitleField = By.cssSelector("#ext-element-579");
    private final By itinerarySelectProfileMenu = By.cssSelector("#ext-togglefield-5");
    private final By itineraryProfileTextField = By.cssSelector("#ext-element-620");
    private final By itineraryProfileFundButton = By.cssSelector("#ext-radiofield-10");
    private final By itineraryProfileInstitutionButton = By.cssSelector("#ext-radiofield-9");
    private final By itineraryProfileContactButton = By.cssSelector("#ext-radiofield-11");
    private final By itineraryCorporateParticipantField = By.cssSelector("#ext-element-629");
    private final By itineraryAddCorporateParticipant = By.cssSelector(".note-itinerary .itinerary-form .note-participants-field .add-participant");
    private final By itineraryVenueField = By.cssSelector("#ext-element-635");
    private final By itineraryAdd = By.cssSelector(".note-itinerary .add-itinerary-button");

    public editNoteDetailsPage(WebDriver driver) {
        super(driver);
    }

    // Change the type of activity.
    // Requires: activity must be one of "Note", "Phone", "Email", "Meeting", "RoadShow"
    public editNoteDetailsPage changeTypeOfActivity(String activity) {
        pause(500L);
        if (activity.equalsIgnoreCase("note")) {
            findElement(changeTypeToNoteButton).click();
        } else if (activity.equalsIgnoreCase("phone")) {
            findElement(changeTypeToPhoneButton).click();
        } else if (activity.equalsIgnoreCase("email")) {
            findElement(changeTypeToEmailButton).click();
        } else if (activity.equalsIgnoreCase("meeting")) {
            findElement(changeTypeToMeetingButton).click();
        } else {
            findElement(changeTypeToRoadshowButton).click();
        }
        return this;
    }

    // Input newTitle into the title field
    public editNoteDetailsPage editTitle(String newTitle) {
        waitForElementToAppear(titleField);
        findElement(titleField).sendKeys(newTitle);
        return this;
    }


    //  Input comment into large text box
    public editNoteDetailsPage editNoteDetails(String comment) {
        waitForElementToAppear(noteField);
        pause(4000L);
        findVisibleElement(noteField).sendKeys(comment);

        return this;
    }

    // Change how the text looks depending on style
    // Requires: style must be one of "Bold", "Italic", "Underline", "Left", "Right", "Center"
    public editNoteDetailsPage changeLookOfText(String style) {
        switch (style.toLowerCase()) {
            case "bold":
                findElement(toggleBoldTextButton).click();
                break;
            case "italic":
                findElement(toggleItalicsButton).click();
                break;
            case "underline":
                findElement(toggleUnderlineButton).click();
                break;
            case "left":
                findElement(toggleLeftIndentButton).click();
                break;
            case "right":
                findElement(toggleRightIndentButton).click();
                break;
            case "center":
                findElement(toggleCenterTextButton).click();
                break;
            default:
                break;
        }
        return this;
    }

    // Change the font size based on given size
    // Requires: size must equal either: 11, 13, 15, 18, 24
    public editNoteDetailsPage changeFontSize(int size) {
        findElement(fontDropdownMenu).click();
        waitForElementToAppear(fontSize11);
        switch (size) {
            case 11:
                findElement(fontSize11).click();
                break;
            case 13:
                findElement(fontSize13).click();
                break;
            case 15:
                findElement(fontSize15).click();
                break;
            case 18:
                findElement(fontSize18).click();
                break;
            case 24:
                findElement(fontSize24).click();
                break;
            default:
                break;
        }
        return this;
    }

    // Add the venue location venueLocation into the venue text box
    public editNoteDetailsPage addVenue(String venueLocation) {
        waitForElementToAppear(venueMenu);
        findElement(venueMenu).clear();
        findElement(venueMenu).sendKeys(venueLocation);
        return this;
    }

    // Edit the location
    public editNoteDetailsPage addLocation(String someLocation) {
        waitForElementToAppear(locationMenu);
        findElement(locationMenu).clear();
        findElement(locationMenu).sendKeys(someLocation);
        return this;
    }

    public editNoteDetailsPage addParticipants(String participant) {
        waitForElementToAppear(corporateTextField);
        findElement(corporateTextField).sendKeys(participant);
        findElement(corporateAddButton).click();

        return this;
    }

    // editAttendees selects a type from the profile menu and adds the keyword to the attendees list
    // Requires: type must be one of: "Institution", "Fund", "Contact"
    //           keyword must exist in the database
    public editNoteDetailsPage editAttendees(String keyword, String type) {
        waitForElementToAppear(profileMenu);
        findElement(profileMenu).click();
        waitForElementToAppear(profileSearchField);
        switch (type.toLowerCase()) {
            case "institution":
                findElement(profileInstitutionButton).click();
                findElement(profileSearchField).sendKeys(keyword);
                waitForElementToAppear(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]"));
                findElement(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]")).click();
                break;
            case "fund":
                findElement(profileFundButton).click();
                findElement(profileSearchField).sendKeys(keyword);
                waitForElementToAppear(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]"));
                findElement(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]")).click();
                break;
            case "contact":
                findElement(profileContactButton).click();
                findElement(profileSearchField).sendKeys(keyword);
                waitForElementToAppear(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]"));
                findElement(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]")).click();
                break;
            default:
                break;
        }
        return this;
    }

    // Changes the start time to the given time
    public editNoteDetailsPage selectStartTime(int time) {
        return this;
    }

    // Saves the edits made
    public editNoteDetailsPage saveChanges() {
        waitForElementToAppear(saveButton);
        findElement(saveButton).click();

        return this;
    }

    public editNoteDetailsPage cancelChanges() {
        waitForElementToAppear(cancelButton);
        findElement(cancelButton).click();

        return this;
    }

    public editNoteDetailsPage changeDate(){
        waitForLoadingScreen();
        scrollToElement(itineraryShowButton);
        Calendar someCalendar = new Calendar(driver).selectStartDate(dateField, itineraryDatePrev,itineraryCurrentMonth,chosenDay);
        return this;
    }

    public editNoteDetailsPage changeStartTime(String time) {
        waitForElementToAppear(startTimeMenu);
        findElement(startTimeMenu).click();
        waitForElementToAppear(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]"));
        findElement(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text() ='" + time + "')]")).click();

        return this;
    }

    public editNoteDetailsPage changeEndTime(String time) {
        waitForElementToAppear(endTimeMenu);
        findElement(endTimeMenu).click();
        waitForElementToAppear(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]"));
        findElement(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]")).click();

        return this;
    }

    public editNoteDetailsPage showItinerary() {
        waitForElementToAppear(itineraryShowButton);
        findElement(itineraryShowButton).click();

        return this;
    }

    public editNoteDetailsPage editItineraryTitle(String title) {
        waitForElementToAppear(itineraryTitleField);
        findElement(itineraryTitleField).sendKeys(title);

        return this;
    }

    public editNoteDetailsPage editItineraryVenue(String venue) {
        waitForElementToAppear(itineraryVenueField);
        findElement(itineraryVenueField).sendKeys(venue);

        return this;
    }

    public editNoteDetailsPage editItineraryParticipant(String participant) {
        waitForElementToAppear(itineraryCorporateParticipantField);
        findElement(itineraryCorporateParticipantField).sendKeys(participant);
        findElement(itineraryAddCorporateParticipant).click();

        return this;
    }

    public editNoteDetailsPage changeItineraryStartTime(String time) {
        waitForElementToAppear(itineraryStartTimeMenu);
        findElement(itineraryStartTimeMenu).click();
        waitForElementToAppear(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]"));
        findElement(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]")).click();

        return this;
    }

    public editNoteDetailsPage changeItineraryEndTime(String time){
        waitForElementToAppear(itineraryEndTimeMenu);
        findElement(itineraryEndTimeMenu).click();
        waitForElementToAppear(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]"));
        findElement(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]")).click();

        return this;
    }

    public editNoteDetailsPage editProfiles (String keyword, String type){
        waitForElementToAppear(itinerarySelectProfileMenu);
        findElement(itinerarySelectProfileMenu).click();
        switch (type.toLowerCase()) {
            case "institution":
                findElement(itineraryProfileInstitutionButton).click();
                findElement(itineraryProfileTextField).sendKeys(keyword);
                waitForElementToAppear(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]"));
                findElement(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]")).click();
                break;
            case "fund":
                findElement(itineraryProfileFundButton).click();
                findElement(itineraryProfileTextField).sendKeys(keyword);
                waitForElementToAppear(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]"));
                findElement(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]")).click();
                break;
            case "contact":
                findElement(itineraryProfileContactButton).click();
                findElement(itineraryProfileTextField).sendKeys(keyword);
                waitForElementToAppear(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]"));
                findElement(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]")).click();
                break;
            default:
                break;
        }
        return this;
    }
    public editNoteDetailsPage changeIntineraryDate(){
        Calendar someCalendar = new Calendar(driver).selectStartDate(itineraryDate, itineraryDatePrev, itineraryCurrentMonth, chosenDay);
        return this;
    }

    public editNoteDetailsPage addItinerary(){
        waitForElementToAppear(itineraryAdd);
        findElement(itineraryAdd).click();
        return this;
    }
}



