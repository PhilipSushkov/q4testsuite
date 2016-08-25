package pageobjects.dashboardPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.securityPage.SecurityOverviewPage;
import pageobjects.contactPage.ContactDetailsPage;
import pageobjects.fundPage.FundPage;
import pageobjects.institutionPage.InstitutionPage;
import pageobjects.logActivity.LogActivityModal;
import pageobjects.reportBuilder.BuildReportModal;

public class Dashboard extends AbstractPageObject {

    // Search field on dashboardPage
    private final By searchField = By.name("search");
    private final By firstCompanyInList = By.cssSelector("span:nth-child(1)");
    private final By clearSearchButton = By.cssSelector(".home-search .x-field-input .x-clear-icon");
    private final By institutionResult = By.id("ext-simplelistitem-6");
    private final By contactResult = By.id("ext-simplelistitem-11");
    private final By fundResult = By.id("ext-simplelistitem-3");

    // Dashboard background (to get out of focused search)
    private final By dashboardGeneral = By.className("x-dock-body");

    // Big and small share price shown on dashboardPage
    private final By bigSharePrice = By.id("ext-home-stock-1");
    private final By smallSharePrice = By.cssSelector(".company-details");

    // Build report icon

    private final By buildReportDashboardButton = By.id("ext-button-14");

    // Log Note icon (unique selectors for dashboard)

    private final By logNoteIcon = By.id("ext-button-10");
    private final By logPhoneIcon = By.id("ext-button-11");
    private final By logEmailIcon = By.id("ext-button-12");
    private final By logMeetingIcon = By.id("ext-button-13");

    public Dashboard(WebDriver driver) {
        super(driver);
    }

    public Dashboard searchForCompany(String companyName) {
        waitForElementToAppear(searchField);
        findElement(searchField).click();
        findElement(searchField).sendKeys(companyName);

        return this;
    }

    public SecurityOverviewPage selectCompanyFromSearch() {
        waitForElementToAppear(firstCompanyInList);
        findElement(firstCompanyInList).click();

        return new SecurityOverviewPage(getDriver());
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

    public SecurityOverviewPage clickBigSharePrice() {
        wait.until(ExpectedConditions.elementToBeClickable(bigSharePrice));
        findElement(bigSharePrice).click();

        return new SecurityOverviewPage(getDriver());
    }

    public SecurityOverviewPage clickSmallSharePrice() {
        wait.until(ExpectedConditions.elementToBeClickable(smallSharePrice));
        findElement(smallSharePrice).click();

        return new SecurityOverviewPage(getDriver());
    }

    public InstitutionPage selectInstitutionFromSearchResults() {
        wait.until(ExpectedConditions.elementToBeClickable(institutionResult));
        findElement(institutionResult).click();

        return new InstitutionPage(getDriver());
    }

    public ContactDetailsPage selectContactFromSearchResults() {
        wait.until(ExpectedConditions.elementToBeClickable(contactResult));
        findElement(contactResult).click();

        return new ContactDetailsPage(getDriver());
    }

    public FundPage selectFundFromSearchResults() {
        wait.until(ExpectedConditions.elementToBeClickable(fundResult));
        findElement(fundResult).click();

        return new FundPage(getDriver());
    }

    public BuildReportModal selectBuildReport() {
        wait.until(ExpectedConditions.elementToBeClickable(buildReportDashboardButton));
        findElement(buildReportDashboardButton).click();

        return new BuildReportModal(getDriver());
    }

    public LogActivityModal logNote() {
        wait.until(ExpectedConditions.elementToBeClickable(logNoteIcon));
        findElement(logNoteIcon).click();

        return new LogActivityModal(getDriver());
    }

    public LogActivityModal logPhoneNote() {
        wait.until(ExpectedConditions.elementToBeClickable(logPhoneIcon));
        findElement(logPhoneIcon).click();

        return new LogActivityModal(getDriver());
    }

    public LogActivityModal logEmailNote() {
        wait.until(ExpectedConditions.elementToBeClickable(logEmailIcon));
        findElement(logEmailIcon).click();

        return new LogActivityModal(getDriver());
    }

    public LogActivityModal logMeetingNote() {
        wait.until(ExpectedConditions.elementToBeClickable(logMeetingIcon));
        findElement(logMeetingIcon).click();

        return new LogActivityModal(getDriver());
    }
}
