package pageobjects.user.logActivity;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.dashboardPage.Dashboard;

/**
 * Created by patrickp on 2016-08-09.
 */
public class LogActivityModal extends AbstractPageObject {

    private final By commentField = By.name("title");
    private final By typeNoteField = By.name("body");
    private final By tagField = By.name("tag");
    private final By postButton = By.cssSelector(".new-note .post-btn");
    private final By nameField = By.xpath("/html/body/div/div/div[1]/form/div[1]/div[2]/div/div[2]/div[4]/div/div[3]/div[2]/div/input");
    private final By cityField = By.name("person_city");
    private final By emailField = By.name("email");
    private final By stateField = By.name("person_state");
    private final By cancelNoteButton = By.cssSelector(".new-note .cancel-btn");
    private final By linkDropdown = By.cssSelector(".link-field");
    private final By keywordField = By.cssSelector(".link-field .search-field.x-field .x-form-field");
    private final By institutionSearchResult = By.cssSelector(".link-field-result .result-item");
    private final By fundIcon = By.id("ext-radiofield-2");
    private final By contactIcon = By.id("ext-radiofield-3");
    private final By companyIcon = By.id("ext-radiofield-4");
    private final By phoneTab = By.id("ext-simplelistitem-2");
    private final By emailTab = By.id("ext-simplelistitem-3");
    private final By meetingTab = By.id("ext-simplelistitem-4");
    private final By meetingDropdown = By.xpath("/html/body/div/div/div[1]/form/div[1]/div[2]/div/div[2]/div[6]/div[2]/div");

    public LogActivityModal(WebDriver driver) {
        super(driver);
    }

    // TODO Remove pauses from these tests and replace with something that won't break when things are running slowly

    public LogActivityModal enterNoteDetails(String comment, String note, String tag) {
        wait.until(ExpectedConditions.elementToBeClickable(commentField));
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();
        pause(2000L);

        return this;
    }

    public LogActivityModal enterPhoneNoteDetails(String comment, String name, String note, String tag) {
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(nameField).click();
        findElement(nameField).sendKeys(name);
        findElement(nameField).sendKeys(Keys.ENTER);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        findElement(postButton).click();
        pause(2000L);

        return this;
    }

    public LogActivityModal enterEmailNoteDetails(String comment, String note, String tag) {
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        findElement(postButton).click();
        pause(2000L);

        return this;
    }

    public LogActivityModal enterMeetingDetails(String comment, String name, String note, String tag) {
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(nameField).sendKeys(name);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        findElement(postButton).click();
        pause(2000L);

        return this;
    }

    public Dashboard cancelNote() {
        findElement(cancelNoteButton).click();
        pause(500L);

        return new Dashboard(getDriver());
    }

    public LogActivityModal linkNoteToInstitution(String institution) {
        findElement(linkDropdown).click();
        findElement(keywordField).sendKeys(institution);
        findElement(institutionSearchResult).click();
        findElement(linkDropdown).click();

        return this;
    }

    public LogActivityModal linkNoteToFund(String fund) {
        findElement(linkDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(fundIcon));
        findElement(fundIcon).click();
        findElement(keywordField).sendKeys(fund);
        findElement(institutionSearchResult).click();
        findElement(linkDropdown).click();

        return this;
    }

    public LogActivityModal linkNoteToContact(String contact) {
        findElement(linkDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(contactIcon));
        findElement(contactIcon).click();
        findElement(keywordField).sendKeys(contact);
        pause(1000L);
        findElement(institutionSearchResult).click();
        findElement(linkDropdown).click();

        return this;
    }

    public LogActivityModal linkNoteToCompany(String company) {
        findElement(linkDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(companyIcon));
        findElement(companyIcon).click();
        findElement(keywordField).sendKeys(company);
        pause(1000L);
        findElement(institutionSearchResult).click();
        findElement(linkDropdown).click();

        return this;
    }

    public LogActivityModal choosePhoneTab() {
        waitForElementToAppear(meetingDropdown);
        findElement(meetingDropdown).click();
        findElement(phoneTab).click();
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();

        return this;
    }

    public LogActivityModal chooseEmailTab() {
        waitForElementToAppear(meetingDropdown);
        findElement(meetingDropdown).click();
        findElement(emailTab).click();
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();

        return this;
    }

    public LogActivityModal chooseMeetingTab() {
        waitForElementToAppear(meetingDropdown);
        findElement(meetingDropdown).click();
        findElement(meetingTab).click();
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();

        return this;
    }
}
