package pageobjects.supportTicketsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-16.
 */
public class SupportTicketsPage extends AbstractPageObject {

    private final By pageTitle = By.cssSelector(".page-header");

    public SupportTicketsPage(WebDriver driver) {
        super(driver);
    }

    public String getSupportTicketsPageTitle() {
        return findElement(pageTitle).getText();
    }
}
