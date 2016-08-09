package pageobjects.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;

/**
 * Created by patrickp on 2016-08-08.
 */
public class ContactPage extends Page {

    private final By contactName = By.className("full-name");

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public String getContactName() {
        return findElement(contactName).getText();
    }
}