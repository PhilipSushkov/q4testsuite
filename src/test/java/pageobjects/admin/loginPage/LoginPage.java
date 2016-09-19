package pageobjects.admin.loginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-16.
 */
public class LoginPage extends AbstractPageObject {
    private final By loginButton = By.cssSelector("#login-page > div > button.login-button");
    private final By googleLoginButton = By.cssSelector("#a0-onestep > div.a0-mode-container > div > form > div.a0-body-content > div.a0-collapse-social > div.a0-iconlist > div > span");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage loginAdmin() {
        findElement(loginButton).click();
        findElement(googleLoginButton).click();

        return this;
    }
}
