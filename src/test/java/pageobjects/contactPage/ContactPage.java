package pageobjects.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-25.
 */
public class ContactPage extends AbstractPageObject {

    private final By pageTitle = By.cssSelector(".page-header");

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public String getContactsPageTitle() {
        return findElement(pageTitle).getText();
    }
}
