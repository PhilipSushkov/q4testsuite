package pageobjects.checklistPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-16.
 */
public class ChecklistPage extends AbstractPageObject{

    private final By pageTitle = By.cssSelector(".page-header");

    public ChecklistPage(WebDriver driver) {
        super(driver);
    }

    public String getChecklistPageTitle() {
        waitForElementToAppear(pageTitle);
        return findElement(pageTitle).getText();
    }
}
