package pageobjects.admin.companyPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-16.
 */
public class CompanyPage extends AbstractPageObject {

    private final By addCompanyButton = By.cssSelector(".page-header .action-buttons .add");
    private final By companyField = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-autocomplete.auto-complete-search .ui-inputtext");
    private final By saveButton = By.cssSelector(".button-yellow");
    private final By cancelButton = By.cssSelector(".button-no-background");
    private final By loading = By.className("outer-spinner-container");
    private final By firstCompany = By.cssSelector("body > q4-app > div > div > q4-organization > p-datatable > div > div > table > tbody > tr:nth-child(1)");
    private final By companyName = By.cssSelector("body > q4-app > div > div > q4-organization > p-datatable > div > div > table > tbody > tr:nth-child(1) > td:nth-child(2)");

    public CompanyPage(WebDriver driver) {
        super(driver);
    }

    public CompanyPage addNewCompany() {
        wait.until(ExpectedConditions.elementToBeClickable(addCompanyButton));
        findElement(addCompanyButton).click();
        findElement(companyField).sendKeys("Microsoft");
        findElement(saveButton).click();

        return this;
    }

    public CompanyPage triggerAddCompanyModal() {
        pause(500L);
        findElement(addCompanyButton).click();

        return this;
    }

    public CompanyPage cancelAddCompany() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        findElement(cancelButton).click();
        pause(5000L);

        return this;
    }

    public CompanyDetailsPage selectFirstCompanyInList() {
        pause(1000L);
        findElement(firstCompany).click();

        return new CompanyDetailsPage(getDriver());
    }

    public String getFirstCompanyName() {
        return findElement(companyName).getText();
    }
}