package pageobjects.loginPage;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.dashboard.Dashboard;
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
    private final By forgotPasswordEmailField = By.id("ext-element-75");
    private final By submitForgottenEmail = By.cssSelector(".forgot-password-form .forgot-password-submit .x-button-label");

    // Invalid email modal (triggered by entering invalid email on password reset)
    private final By invalidEmailErrorModal= By.id("ext-messagebox-1");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    JavascriptExecutor js;

    public Dashboard loginUser(String email, String password) {
        waitForElementToAppear(emailField);
        findElement(emailField).clear();
        findElement(passwordField).clear();
        findElement(emailField).sendKeys(email);
        findElement(passwordField).sendKeys(password);
        pause(2000L);
        waitForElementToAppear(loginButton);
        retryClick(loginButton);

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
        findElement(forgotPasswordLink).click();

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
}
