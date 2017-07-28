package pageobjects.user.activityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;

/**
 * Created by dannyl on 2017-06-20.
 */
public class LogActivityPage extends AbstractPageObject{
    private final By cancelActivityButton = By.xpath("//div[contains(@class,'x-unsized x-button form-button no-background x-button-no-icon')]");
    private final By titleField = By.name("title");
    private final By saveButton = By.xpath("//span[@class='x-button-label'][text()='Save']");
    private final By selectInstitutionButton = By.id("ext-radiofield-10");
    private final By keywordField = By.xpath("//div[contains(@class, 'x-container x-field x-field-text x-label-align-left typeaheaded-search x-form-label-nowrap x-empty')]/div[contains(@class,'x-component-outer')]/div[contains(@class,'x-unsized x-field-input')]/input[contains(@class, 'x-input-el x-form-field x-input-text')]");
    private  String keyword = "";
    private final By keywordSearchDropdown = By.xpath("//div[contains(@class, 'x-inner x-dataview-inner')]/div[contains(text(), '" + keyword + "')]");
    private final By typeNoteField = By.xpath("//div[contains(@class, 'x-container x-field x-field-textarea x-label-align-left x-form-label-nowrap x-paint-monitored')]/div[contains(@class, 'x-component-outer')]/div[contains(@class, 'x-unsized x-field-input')]/div[contains(@class, 'mce-tinymce mce-container mce-panel')]/div[contains(@class, 'mce-container-body mce-stack-layout')]/div[contains(@class, 'mce-edit-area mce-container mce-panel mce-stack-layout-item mce-last')]/*[contains(@title, 'Rich Text')]");
    private final By firstItemInDropdown = By.xpath("//div[contains(@class, 'x-inner x-dataview-inner')]/div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item result-item')][1]");
    private final By tagField = By.name("tag");
    private final By locationField = By.xpath("//div[contains(@class, 'x-container activity-location-field capitalized x-label-align-left x-form-label-nowrap')]/div[contains(@class, 'x-component-outer')]/div[contains(@class, 'x-container x-unsized')]/div[contains(@class, 'x-inner')]/div[contains(@class, 'x-container x-field x-field-text x-label-align-left x-form-label-nowrap x-empty')]/div[contains(@class, 'x-component-outer')]/div[contains(@class, 'x-unsized x-field-input')]/*[contains(@type, 'text')]");
    private final By nameField = By.xpath("//div[contains(@class, 'x-container x-field x-field-text x-label-align-left participants-field')]/div[contains(@class, 'x-component-outer')]/div[contains(@class, 'x-unsized x-field-input')]/*[contains(@type, 'text')]");
    private final By fundIcon = By.id("ext-radiofield-11");
    private final By contactIcon = By.id("ext-radiofield-12");
    private final By phoneTab = By.id("ext-radiofield-2");
    private final By emailTab = By.id("ext-radiofield-3");
    private final By meetingTab = By.id("ext-radiofield-4");
    private final By roadshowTab = By.id("ext-radiofield-5");
    private final By firstLocationFieldData = By.xpath("//div[contains(@class,'x-unsized x-list-item x-list-item-tpl x-list-item-relative");

    public LogActivityPage(WebDriver driver) {
        super(driver);
    }

    public LogActivityPage enterNoteDetails(String comment, String note, String tag) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(titleField));
        waitForElementToAppear(titleField);
        retryClick(findElement(titleField));
        findElement(titleField).sendKeys(comment);
        findElement(typeNoteField).sendKeys(note);
        // Tags don't exist on new activity page
     /*   findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN); */
        scrollToElement(saveButton);
        waitForElementToBeClickable(saveButton).click();
        waitForLoadingScreen();

        return this;
    }

    public LogActivityPage enterPhoneNoteDetails(String comment, String name, String note, String tag) {
        waitForElementToBeClickable(titleField);
        retryClick(findElement(titleField));
        findElement(titleField).sendKeys(comment);
        findElement(nameField).click();
        findElement(nameField).sendKeys(name);
        findElement(nameField).sendKeys(Keys.ENTER);
        findElement(typeNoteField).sendKeys(note);
        // Tags don't exist on new activity page
     /*   findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN); */

        return this;
    }

    public LogActivityPage enterEmailNoteDetails(String comment, String note, String tag) {
        waitForElementToBeClickable(titleField);
        retryClick(findElement(titleField));
        findElement(titleField).sendKeys(comment);
        findElement(typeNoteField).sendKeys(note);
        // Tags don't exist on new activity page
     /*   findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN); */

        return this;
    }

    public LogActivityPage enterMeetingDetails(String comment, String name, String note, String tag) {
        waitForElementToBeClickable(titleField);
        retryClick(findElement(titleField));
        findElement(titleField).sendKeys(comment);
        findElement(nameField).sendKeys(name);
        findElement(typeNoteField).sendKeys(note);
        // Tags don't exist on new activity page
     /*   findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN); */

        return this;
    }

    public LogActivityPage enterRoadshowDetails(String title, String location, String tag){
        //The waitForLoadingScreen() makes the test a bit slower than I'd like
        //But at least now it's waiting for something instead of pause()
        waitForLoadingScreen();
        waitForElementToBeClickable(titleField);
        retryClick(findElement(titleField));
        findElement(titleField).sendKeys(title);
        findElement(locationField).sendKeys(location);

        // Click to get rid of location dropdown menu
        pause(500L);
        clickElementLocation(titleField);

        // Tags don't exist on new activity page
     /*   findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN); */

        return this;
    }

    public ActivityPage cancelNote() {
        waitForElementToBeClickable(cancelActivityButton).click();

        return new ActivityPage(getDriver());
    }

    public LogActivityPage linkNoteToInstitution(String institution) {
        keyword = institution;
        waitForElement(selectInstitutionButton);
        scrollToElement(selectInstitutionButton);
        findElement(selectInstitutionButton).click();
        findElement(keywordField).sendKeys(institution);
        waitForElementToBeClickable(keywordSearchDropdown).click();

        return this;
    }

     public LogActivityPage linkNoteToFund(String fund) {
        keyword = fund;
        waitForElement(fundIcon);
        scrollToElement(fundIcon);
        findElement(fundIcon).click();
        findElement(keywordField).sendKeys(fund);
        waitForElementToBeClickable(keywordSearchDropdown).click();

        return this;
    }

    public LogActivityPage linkNoteToContact(String contact) {
        keyword = contact;
        waitForElement(contactIcon);
        scrollToElement(contactIcon);
        findElement(contactIcon).click();
        findElement(keywordField).sendKeys(contact);
        pause(5000);
        scrollToElement(firstItemInDropdown);
        findElement(firstItemInDropdown).click();

        return this;
    }

    public LogActivityPage choosePhoneTab() {
        waitForElementToBeClickable(phoneTab).click();
        waitForElementToBeClickable(saveButton).click();

        return this;
    }

    public LogActivityPage chooseEmailTab() {
        waitForElementToBeClickable(emailTab).click();
        waitForElementToBeClickable(saveButton).click();

        return this;
    }

    public LogActivityPage chooseMeetingTab() {
        waitForElementToBeClickable(meetingTab).click();
        waitForElementToBeClickable(saveButton).click();

        return this;
    }

    public LogActivityPage chooseRoadshowTab() {
        waitForLoadingScreen();
        waitForElementToBeClickable(roadshowTab);
        retryClick(roadshowTab);
        findVisibleElement(saveButton).click();

        return this;
    }

    public NoteDetailsPage postActivity() {
        waitForElementToAppear(saveButton);
        clickElementLocation(saveButton);
        pause(2000);

        return new NoteDetailsPage(getDriver());
    }
}
