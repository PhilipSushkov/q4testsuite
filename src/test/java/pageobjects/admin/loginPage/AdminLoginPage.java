package pageobjects.admin.loginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-16.
 */
public class AdminLoginPage extends AbstractPageObject {
    private final By loginButton = By.cssSelector("#login-page > div > button.login-button");
    private final By googleLoginButton = By.className("auth0-lock-social-button-text");
    private final By enterEmail = By.id("Email");
    private final By enterPassword = By.id("Passwd");
    private final By submit = By.id("signIn");
    private final By nextButton = By.id("next");

    public AdminLoginPage(WebDriver driver) {
        super(driver);
    }

    public AdminLoginPage customLoginAdmin(String email, String password) {
        pause(5000L);
        // Store the current window handle
        String winHandleBefore = driver.getWindowHandle();

        findElement(loginButton).click();
        findElement(googleLoginButton).click();

        // Perform the click operation that opens new window

        // Switch to new window opened
        windowDidLoad("Sign in - Google Accounts");
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // Perform the actions on new window
        findElement(enterEmail).sendKeys(email);
        findElement(nextButton).click();
        findElement(enterPassword).sendKeys(password);
        findElement(submit).click();
        pause(2000L);
        //String mainWindowTitle = "";
        // Switch back to original browser (first window)
        //windowDidLoad(mainWindowTitle);
        driver.switchTo().window(winHandleBefore);

        return this;
    }

    public AdminLoginPage loginAdmin() {
        // Store the current window handle
        String winHandleBefore = driver.getWindowHandle();

        // Perform the click operation that opens new window
        findElement(loginButton).click();
        waitForElementToAppear(googleLoginButton);
        findElement(googleLoginButton).click();

        // Switch to new window opened
        windowDidLoad("Sign in - Google Accounts");
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // Perform the actions on new window
        findElement(enterEmail).sendKeys("test@q4websystems.com");
        findElement(nextButton).click();
        findElement(enterPassword).sendKeys("testing!");
        findElement(submit).click();
        pause(2000L);

        // Switch back to original browser (first window)
        driver.switchTo().window(winHandleBefore);

        return this;
    }

}
