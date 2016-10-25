package pageobjects;

import org.apache.commons.collections4.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.admin.companyPage.CompanyPage;
import pageobjects.admin.implementationPage.ImplementationPage;
import pageobjects.admin.profilesPage.ProfilesList;
import pageobjects.user.logActivity.LogActivityModal;
import pageobjects.user.sideNavBar.SideNavBar;

public class AbstractPageObject implements PageObject {

    public final WebDriver driver;

    public final WebDriverWait wait;

    private final Predicate<WebElement> displayedElementPredicate = new Predicate<WebElement>() {
        @Override
        public boolean evaluate(WebElement t) {
            return t.isDisplayed();
        }
    };

    // Side hamburger menu icon
    private final By sideNavIcon = By.cssSelector(".page-home .menu-btn"); //use for dashboard
    private final By hamburgerIcon = By.cssSelector(".navigation-toggler .x-button-icon"); //use for other pages

    private final By pageTitle = By.cssSelector(".q4-hero-banner .page-title");
    private final By otherPageTitle = By.cssSelector(".q4-hero-banner .page-title h1");

    // Admin page elements
    private final By companyPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(2) > a > i");
    private final By adminPageTitle = By.cssSelector(".page-header .page-title .details h2");
    private final By profilesPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(3) > a > i");
    private final By loading = By.className("outer-spinner-container");
    private final By implementationPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(5) > a > i");

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5L);
    }

    @Override
    public WebDriver getDriver() {
        return this.driver;
    }

    @Override
    public Predicate<WebElement> getDisplayedElementPredicate() {
        return this.displayedElementPredicate;
    }

    @Override
    public WebDriverWait getWait() {
        return this.wait;
    }

    // Check if subscription toggle is unchecked. If its unchecked, check it
    public void toggleOn(By selector) {
        String value = findElement(selector).getAttribute("class");
        if (value.equals("off ng-untouched ng-pristine ng-valid")) {
            findElement(selector).click();
        }
    }

    // Check if subscription toggle is check. If its checked, un-check it
    public void toggleOff(By selector) {
        String value = findElement(selector).getAttribute("class");
        System.out.println(value);
        if (value.equals("on ng-untouched ng-pristine ng-valid")) {
            findElement(selector).click();
        }
    }

    //use from dashboard
    public SideNavBar accessSideNav() {
        waitForLoadingScreen();
        findElement(sideNavIcon).click();

        return new SideNavBar(getDriver());
    }

    //use from other pages
    public SideNavBar accessSideNavFromPage() {
        findElement(hamburgerIcon).click();
        return new SideNavBar(getDriver());
    }

    public String getPageTitle() {
        pause(500L);
        return findElement(pageTitle).getText();
    }

    // Some pages have a different header
    public String getOtherPageTitle() {
        pause(500L);
        return findElement(otherPageTitle).getText();
    }

    public LogActivityModal pageRefresh() {
        pause(2000L);
        driver.navigate().refresh();

        return new LogActivityModal(getDriver());
    }

    public void switchToNewTab() {
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
    }

    public CompanyPage navigateToCompanyPage() {
        waitForLoadingScreen();
        findElement(companyPage).click();

        return new CompanyPage(getDriver());
    }

    public String getAdminPageTitle() {
        return findElement(adminPageTitle).getText();
    }

    public ProfilesList navigateToProfilesPage() {
        waitForLoadingScreen();
        findElement(profilesPage).click();
        waitForLoadingScreen();

        return new ProfilesList(driver);
    }

    public ImplementationPage navigateToImplementationPage() {
        waitForLoadingScreen();
        findElement(implementationPage).click();

        return new ImplementationPage(getDriver());
    }

    public void waitForLoadingScreen() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loading));
    }
}
