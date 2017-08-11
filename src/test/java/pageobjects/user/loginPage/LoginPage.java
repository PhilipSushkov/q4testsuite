package pageobjects.user.loginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.Page;

/**
 * Created by patrickp on 2016-08-03.
 */
public class LoginPage extends Page {

    // Log in form
    private final By emailField = By.name("user");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector(".login .x-button.login-button");

    // Error modal
    private final By incorrectUsernamePasswordErrorModal = By.id("ext-messagebox-1");
    private final By okButtonInErrorModal = By.cssSelector(".x-button-no-icon > .x-button-label");

    // Forgot password modal
    private final By forgotPasswordLink = By.cssSelector(".login .x-button.forgot-pass-button");
    private final By forgotPasswordModalTitle = By.cssSelector(".forgot-password-form h1");
    private final By cancelForgotPassword = By.cssSelector(".forgot-password-form .forgot-password-cancel .x-button-label, .forgot-password-form .forgot-password-submit .x-button-label");
    private final By forgotPasswordEmailField = By.id("ext-element-73");
    private final By submitForgottenEmail = By.cssSelector(".forgot-password-form .forgot-password-submit .x-button-label");

    // Invalid email modal (triggered by entering invalid email on password reset)
    private final By invalidEmailErrorModal= By.id("ext-messagebox-1");

    // Valid email confirmation modal
    private final By confirmationModal = By.id("ext-messagebox-1");
    private final By confirmationOkButton = By.cssSelector(".x-msgbox-buttons .x-button");

    // Longer WebDriverWait for initial loading screen
    WebDriverWait longWait = new WebDriverWait(driver, 20);

    public LoginPage(WebDriver driver) {
        super(driver);
        disableAnimations();
    }

    public Dashboard loginUser() {
        longWait.until(ExpectedConditions.elementToBeClickable(emailField)).clear();
        findElement(passwordField).clear();
        findElement(emailField).sendKeys("patrickp@q4websystems.com");
        findElement(passwordField).sendKeys("patrick!");
        waitForElementToBeClickable(loginButton).click();
        waitForDashboardToLoad();
        pause(1000L);

        return new Dashboard(getDriver());
    }

    public Dashboard loginUnsubscribedUser() {
        longWait.until(ExpectedConditions.elementToBeClickable(emailField)).clear();
        findElement(passwordField).clear();
        findElement(emailField).sendKeys("test@q4websystems.com");
        findElement(passwordField).sendKeys("q4pass1234!");
        waitForElementToBeClickable(loginButton).click();
        waitForDashboardToLoad();
        pause(1000L);

        return new Dashboard(getDriver());
    }

    public Dashboard customLoginUser(String email, String password) {
        longWait.until(ExpectedConditions.elementToBeClickable(emailField)).clear();
        findElement(passwordField).clear();
        findElement(emailField).sendKeys(email);
        findElement(passwordField).sendKeys(password);
        waitForElementToBeClickable(loginButton).click();

        return new Dashboard(getDriver());
    }

    public String getErrorMessage() {
        waitForElementToAppear(incorrectUsernamePasswordErrorModal);
        return findElement(incorrectUsernamePasswordErrorModal).getText();
    }

    public LoginPage dismissErrorModal() {
        waitForElementToAppear(incorrectUsernamePasswordErrorModal);
        findElement(okButtonInErrorModal).click();

        return this;
    }

    public LoginPage forgotPassword() {
        waitForLoadingScreen();
        waitForElementToBeClickable(forgotPasswordLink).click();

        return this;
    }

    public String getForgotPasswordModalTitle() {

        waitForElementToAppear(forgotPasswordModalTitle);
        return findElement(forgotPasswordModalTitle).getText();
    }

    public LoginPage dismissForgotPasswordModal() {

        waitForElementToAppear(cancelForgotPassword);
        findElement(cancelForgotPassword).click();

        return this;
    }

    public LoginPage enterForgotPasswordEmail(String email) {

        waitForElementToAppear(forgotPasswordEmailField);
        findElement(forgotPasswordEmailField).click();
        findElement(forgotPasswordEmailField).sendKeys(email);
        findElement(submitForgottenEmail).click();

        return  this;
    }

    public String getInvalidEmailError() {
        waitForElementToAppear(invalidEmailErrorModal);
        return findElement(invalidEmailErrorModal).getText();
    }

    public String getConfirmationText() {
        waitForElementToAppear(confirmationModal);
        return findElement(confirmationModal).getText();
    }

    public LoginPage dismissConfirmationModal() {
        waitForElementToAppear(confirmationOkButton);
        findElement(confirmationOkButton).click();

        return this;
    }
}
