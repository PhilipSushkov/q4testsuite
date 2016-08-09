package pageobjects.logActivity;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-09.
 */
public class LogActivity extends AbstractPageObject {

    private final By commentField = By.name("title");
    private final By typeNoteField = By.name("body");
    private final By tagField = By.name("tag");
    private final By postButton = By.id("ext-button-22");
    private final By phoneNumberField = By.name("person_phone");
    private final By nameField = By.name("person_name");
    private final By cityField = By.name("person_city");
    private final By emailField = By.name("email");
    private final By stateField = By.name("person_state");

    public LogActivity(WebDriver driver) {
        super(driver);
    }

    public LogActivity enterNoteDetails(String comment, String note, String tag) {
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

    public LogActivity enterPhoneNoteDetails(String comment, String phone, String name, String city, String note, String tag) {
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(phoneNumberField).sendKeys(phone);
        findElement(nameField).sendKeys(name);
        findElement(cityField).sendKeys(city);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();
        pause(2000L);

        return this;
    }

    public LogActivity enterEmailNoteDetails(String comment, String email, String note, String tag) {
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(emailField).sendKeys(email);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();
        pause(2000L);

        return this;
    }

    public LogActivity enterMeetingDetails(String comment, String name, String city, String state, String note, String tag) {
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(nameField).sendKeys(name);
        findElement(cityField).sendKeys(city);
        findElement(stateField).sendKeys(state);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();
        pause(2000L);

        return this;
    }
}
