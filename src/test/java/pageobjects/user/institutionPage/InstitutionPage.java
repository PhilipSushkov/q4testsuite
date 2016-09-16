package pageobjects.user.institutionPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;

/**
 * Created by patrickp on 2016-08-08.
 */
public class InstitutionPage extends Page {

    private final By institutionName  = By.className("page-title");

    public InstitutionPage(WebDriver driver) {
        super(driver);
    }


    public String getInstitutionName() {
        waitForElementToAppear(institutionName);
        return findElement(institutionName).getText();
    }
}
