package pageobjects.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.Page;

/**
 * Created by patrickp on 2016-08-08.
 */
public class ContactDetailsPage extends Page {

    private final By contactName = By.className("contact-name");
    private final By contactDropDown = By.cssSelector(".contact-page .utility-menu .more-button");
    private final By contactDropdownPostAdd = By.cssSelector(".contact-page .utility-menu .more-button");
    private final By addOption = By.id("ext-button-47");
    private final By favIcon = By.cssSelector(".contact-page .contact-header .contact-favorite");
    private final By removeFromContacts = By.id("ext-button-45");
    private final By addTag = By.cssSelector(".tags-view .tag.add-button");
    private final By tagInputField = By.cssSelector(".tags-modal-list .tag-field input");
    private final By contactTags = By.cssSelector(".contact-hero-banner .tags-view");

    public ContactDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getContactName() {
        return findElement(contactName).getText();
    }

    public ContactDetailsPage addToContacts() {
        pause(500L);
        wait.until(ExpectedConditions.elementToBeClickable(contactDropDown));
        findElement(contactDropDown).click();
        wait.until(ExpectedConditions.elementToBeClickable(addOption));
        findElement(addOption).click();
        pause(500L);

        return this;
    }

    public ContactDetailsPage removeContactFromList() {
        pause(500L);
        driver.navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(contactDropdownPostAdd));
        retryClick(contactDropdownPostAdd);
        wait.until(ExpectedConditions.elementToBeClickable(removeFromContacts));
        findElement(removeFromContacts).click();

        return this;
    }

    public ContactDetailsPage addTagToContact(String tag) {
        pause(500L);
        wait.until(ExpectedConditions.elementToBeClickable(addTag));
        findElement(addTag).click();
        wait.until(ExpectedConditions.elementToBeClickable(tagInputField));
        findElement(tagInputField).sendKeys(tag);
        findElement(tagInputField).sendKeys(Keys.RETURN);
        driver.navigate().refresh();

        return this;
    }

    public String getContactTags() {
        waitForElementToAppear(contactTags);
        return findElement(contactTags).getText();
    }
}
