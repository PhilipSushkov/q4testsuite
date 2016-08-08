package pageobjects.dashboard;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.companyDetails.CompanyPage;
import pageobjects.sideNavBar.SideNavBar;

public class Dashboard extends AbstractPageObject {
    private final By searchField = By.name("search");
    private final By firstCompanyInList = By.cssSelector("span:nth-child(1)");
    private final By clearSearchButton = By.cssSelector(".home-search .x-field-input .x-clear-icon");
    private final By dashboardGeneral = By.className("x-dock-body");
    private final By sideNavIcon = By.cssSelector(".page-home .menu-btn");

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

    public SideNavBar accessSideNav() {
        wait.until(ExpectedConditions.elementToBeClickable(sideNavIcon));
        findElement(sideNavIcon).click();

        return new SideNavBar(getDriver());
    }

    public String getSearchFieldText() {
        return findElement(searchField).getAttribute("placeholder");
    }
}
