package pageobjects.admin.companyPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompanyDetailsPage extends CompanyPage {

    private final By addButton = By.cssSelector(".button.button-blue");
    private final By companySearchField = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-autocomplete.auto-complete-search .ui-inputtext");
    private final By saveButton = By.cssSelector(".button-yellow");
    private final By searchResult = By.cssSelector("body > q4-app > div > div > q4-organization-details > q4-organization-peers > p-dialog:nth-child(3) > div > div.ui-dialog-content.ui-widget-content > q4-peer-create > p-autocomplete > span > div");

    public CompanyDetailsPage(WebDriver driver) {
        super(driver);
    }

    public CompanyDetailsPage addPeer(String company) {
        waitForElementToAppear(addButton);
        findElement(addButton).click();
        findElement(companySearchField).sendKeys(company);
        findElement(searchResult).click();
        findElement(saveButton).click();

        return this;
    }
}
