package pageobjects.admin.companyPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompanyDetailsPage extends CompanyPage {

    private final By addButton = By.cssSelector(".square-button.add");
    private final By companySearchField = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-autocomplete.auto-complete-search .ui-inputtext");
    private final By saveButton = By.xpath("/html/body/q4-app/div/div/q4-organization-details/q4-organization-peers/p-dialog[1]/div/div[2]/q4-peer-create/div/button[2]");
    private final By companyNameSaveButton = By.xpath("/html/body/q4-app/div/div/q4-organization-details/p-dialog[2]/div/div[2]/q4-organization-details-edit/div/button");

    private final By searchResult = By.cssSelector("body > q4-app > div > div > q4-organization-details > q4-organization-peers > p-dialog:nth-child(3) > div > div.ui-dialog-content.ui-widget-content > q4-peer-create > p-autocomplete > span > div");

    private final By editCompanyButton = By.xpath("/html/body/q4-app/div/div/q4-organization-details/header/div/div[2]/button[1]");
    private final By companyNameField = By.xpath("//p-dialog[contains(@header,'Edit Company Name')]//input[contains(@placeholder,'Name')]");
    private final By companyEditField = By.name("company_name");

    public CompanyDetailsPage(WebDriver driver) {
        super(driver);
    }

    public CompanyDetailsPage addPeer(String company) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        findElement(addButton).click();
        findElement(companySearchField).sendKeys(company);
        waitForElementToAppear(searchResult);
        findElement(searchResult).click();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        findElement(saveButton).click();

        return this;
    }

    public CompanyDetailsPage editCompanyName(String newName) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(editCompanyButton));
        findElement(editCompanyButton).click();
        findElement(companyNameField).clear();
        findElement(companyNameField).sendKeys(newName);
        findElement(companyNameSaveButton).click();

        return this;
    }

    public CompanyPage addEditPeer(String name, String newName) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        findElement(addButton).click();
        findElement(companySearchField).sendKeys(name);
        waitForElementToAppear(searchResult);
        findElement(searchResult).click();
        findElement(companyEditField).clear();
        findElement(companyEditField).sendKeys(newName);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        findElement(saveButton).click();

        return this;
    }
}
