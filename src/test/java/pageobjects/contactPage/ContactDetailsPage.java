package pageobjects.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.Page;

/**
 * Created by patrickp on 2016-08-08.
 */
public class ContactDetailsPage extends Page {

    private final By contactName = By.className("full-name");
    private final By contactDropDown = By.cssSelector(".contact-page .utility-menu .more-button");
    private final By addOption = By.id("ext-button-47");
    private final By favIcon = By.cssSelector(".contact-page .contact-header .contact-favorite");

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
}
