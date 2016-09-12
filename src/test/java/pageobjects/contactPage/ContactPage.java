package pageobjects.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-25.
 */
public class ContactPage extends AbstractPageObject {

    private final By contactList = By.cssSelector(".contact-favorite-list");
    private final By firstContactInList = By.cssSelector(".contact-favorite-list .x-dataview-item:last-child .view-list-item.contact-details");

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public String getContacts() {
        pause(500L);
        return findElement(contactList).getText();
    }

    public ContactDetailsPage viewContactDetails() {
        findElement(firstContactInList).click();
        
        return new ContactDetailsPage(getDriver());
    }
}
