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

    // Log Note icon (visible on multiple pages)

    private final By logNoteIcon = By.id("ext-button-10");
    private final By logPhoneIcon = By.id("ext-button-11");
    private final By logEmailIcon = By.id("ext-button-12");
    private final By logMeetingIcon = By.id("ext-button-13");

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
