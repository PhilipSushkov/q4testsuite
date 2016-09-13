package pageobjects;

import org.apache.commons.collections4.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.logActivity.LogActivityModal;
import pageobjects.sideNavBar.SideNavBar;

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
    private final By sideNavIcon = By.cssSelector(".page-home .menu-btn");
    private final By hamburgerIcon = By.cssSelector(".navigation-toggler .x-button-icon");

    private final By pageTitle = By.cssSelector(".q4-hero-banner .page-title");

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15L);
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

    public void checkIfUnchecked(By selector) {
        String value = findElement(selector).getAttribute("checked");
        if (!Boolean.parseBoolean(value)) {
            findElement(selector).click();
        }
    }

    public SideNavBar accessSideNav() {
        wait.until(ExpectedConditions.elementToBeClickable(sideNavIcon));
        findElement(sideNavIcon).click();

        return new SideNavBar(getDriver());
    }

    public SideNavBar accessSideNavFromPage() {
        findElement(hamburgerIcon).click();
        return new SideNavBar(getDriver());
    }

    public String getPageTitle() {
        pause(500L);
        return findElement(pageTitle).getText();
    }

    public LogActivityModal pageRefresh() {
        driver.navigate().refresh();

        return new LogActivityModal(getDriver());
    }
}
