package pageobjects.dashboard;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.companyPage.CompanyPage;
import pageobjects.contactPage.ContactPage;
import pageobjects.fundPage.FundPage;
import pageobjects.institutionPage.InstitutionPage;
import pageobjects.sideNavBar.SideNavBar;

public class Dashboard extends AbstractPageObject {
    // Search field on dashboard
    private final By searchField = By.name("search");
    private final By firstCompanyInList = By.cssSelector("span:nth-child(1)");
    private final By clearSearchButton = By.cssSelector(".home-search .x-field-input .x-clear-icon");
    private final By institutionResult = By.id("ext-simplelistitem-16");
    private final By contactResult = By.id("ext-simplelistitem-6");
    private final By fundResult = By.id("ext-simplelistitem-3");

    // Dashboard background (to get out of focused search)
    private final By dashboardGeneral = By.className("x-dock-body");

    // Big and small share price shown on dashboard
    private final By bigSharePrice = By.id("ext-home-stock-1");
    private final By smallSharePrice = By.cssSelector(".company-details");

    public Dashboard(WebDriver driver) {
        super(driver);
    }

    public Dashboard searchForCompany(String companyName) {
        waitForElementToAppear(searchField);
        findElement(searchField).click();
        findElement(searchField).sendKeys(companyName);

        return this;
    }

    public CompanyPage selectCompanyFromSearch() {
        waitForElementToAppear(firstCompanyInList);
        findElement(firstCompanyInList).click();

        return new CompanyPage(getDriver());
    }

    public Dashboard clearSearchField() {
        waitForElementToAppear(dashboardGeneral);
        findElement(dashboardGeneral).click();
        pause(2000L);
        retryClick(clearSearchButton);

        return this;
    }

    public String getSearchFieldText() {
        return findElement(searchField).getAttribute("placeholder");
    }

    public CompanyPage clickBigSharePrice() {
        wait.until(ExpectedConditions.elementToBeClickable(bigSharePrice));
        findElement(bigSharePrice).click();

        return new CompanyPage(getDriver());
    }

    public CompanyPage clickSmallSharePrice() {
        wait.until(ExpectedConditions.elementToBeClickable(smallSharePrice));
        findElement(smallSharePrice).click();

        return new CompanyPage(getDriver());
    }

    public InstitutionPage selectInstitutionFromSearchResults() {
        wait.until(ExpectedConditions.elementToBeClickable(institutionResult));
        findElement(institutionResult).click();

        return new InstitutionPage(getDriver());
    }

    public ContactPage selectContactFromSearchResults() {
        wait.until(ExpectedConditions.elementToBeClickable(contactResult));
        findElement(contactResult).click();

        return new ContactPage(getDriver());
    }

    public FundPage selectFundFromSearchResults() {
        wait.until(ExpectedConditions.elementToBeClickable(fundResult));
        findElement(fundResult).click();

        return new FundPage(getDriver());
    }
}
