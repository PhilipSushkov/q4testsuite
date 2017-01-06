package pageobjects.user.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-25.
 */
public class ContactPage extends AbstractPageObject {

    private final By contactList = By.cssSelector(".contact-favorite-list");
    private final By firstContactInList = By.cssSelector(".contact-favorite-list-container .contact-favorite-list .x-dataview-item .column:nth-child(2)");
    private final By searchField = By.name("contactsFilterSearch");
    private final By showMoreLink = By.cssSelector(".load-more .x-button");
    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public String getContacts() {
        waitForLoadingScreen();
        loadAllContacts();
        return findElement(contactList).getText();
    }

    public void loadAllContacts(){
            while(findElement(showMoreLink).isDisplayed()) {
                findElement(showMoreLink).click();
                waitForLoadingScreen();
            }
    }

    public ContactDetailsPage viewContactDetails() {
        findElement(firstContactInList).click();
        
        return new ContactDetailsPage(getDriver());
    }

    public ContactPage searchForContact(String name) {
        findElement(searchField).click();
        findElement(searchField).sendKeys(name);
        findElement(searchField).sendKeys(Keys.ENTER);

        return this;
    }
}
