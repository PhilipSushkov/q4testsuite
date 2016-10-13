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
    private final By searchField = By.cssSelector(".search.dark .search-input");
    private final By companyList = By.cssSelector(".ui-datatable table");
    private final By companyHeaderName = By.cssSelector(".page-header .page-title .details h2");
    private final By peerList = By.cssSelector(".ui-datatable table");
    private final By deletePeerButton = By.cssSelector("body > q4-app > div > div > q4-organization-details > q4-organization-peers > p-datatable > div > div > table > tbody > tr.ui-widget-content.ui-datatable-even > td.action-buttons > span > button.square-button.button-no-background.remove");
    private final By firstSearchResult = By.cssSelector("body > q4-app > div > div > q4-organization > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-organization-create > p-autocomplete > span > div > ul > li:nth-child(1)");
    private final By modalX = By.className("ui-dialog-titlebar-icon ui-dialog-titlebar-close ui-corner-all");

    public CompanyPage(WebDriver driver) {
        super(driver);
    }

    public CompanyPage addNewCompany(String companyName) {
        wait.until(ExpectedConditions.elementToBeClickable(addCompanyButton));
        findElement(addCompanyButton).click();
        findElement(companyField).sendKeys(companyName);
        pause(500L);
        findElement(firstSearchResult).click();
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

    public CompanyPage searchForCompany(String companyName) {
        findElement(searchField).sendKeys(companyName);

        return this;
    }

    public String getCompanyList() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loading));
        return findElement(companyList).getText();
    }

    public String getCompanyName() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loading));
        return findElement(companyHeaderName).getText();
    }

    public String getPeerList() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loading));
        return findElement(peerList).getText();
    }

    public CompanyPage removePeer() {
        findElement(deletePeerButton).click();

        return this;
    }

    public CompanyPage exitAddCompanyModal() {
        findElement(modalX).click();

        return this;
    }
}