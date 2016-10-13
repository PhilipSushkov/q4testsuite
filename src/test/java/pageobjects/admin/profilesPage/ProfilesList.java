package pageobjects.admin.profilesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-10-11.
 */
public class ProfilesList extends AbstractPageObject {
    private final By profileSearchField = By.cssSelector(".search.dark .search-input");
    private final By profileList = By.cssSelector(".ui-datatable table");

    private final By loadingSpinner = By.className("outer-spinner-container");
    private final By addProfileButton = By.cssSelector(".page-header .action-buttons .add");

    //Add profile modal
    private final By emailField = By.name("user");
    private final By passwordField = By.name("password");
    private final By passwordConfirmField = By.name("confirmPassword");
    private final By organizationField = By.cssSelector("body > q4-app > div > div > q4-profile > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-profile-create > form > p-autocomplete > span > input");
    private final By firstNameField = By.name("firstName");
    private final By lastNameField = By.name("lastName");
    private final By titleField = By.name("title");
    private final By phoneField = By.name("phone");
    private final By saveButton = By.cssSelector("body > q4-app > div > div > q4-profile > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-profile-create > form > div > button.button.button-yellow");
    private final By firstSearchResult = By.cssSelector("body > q4-app > div > div > q4-profile > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-profile-create > form > p-autocomplete > span > div > ul > li");

    // Profile List
    private final By firstProfileInList = By.cssSelector(".ui-datatable tbody");

    public ProfilesList(WebDriver driver) {
        super(driver);
    }

    public ProfilesList searchForProfile(String username) {
        findElement(profileSearchField).sendKeys(username);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));

        return this;
    }

    public String getProfileList() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        return findElement(profileList).getText();
    }

    public ProfilesList addNewProfile(String email, String password, String organization, String firstName, String lastName, String title, String phone) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        findElement(addProfileButton).click();
        findElement(emailField).sendKeys(email);
        findElement(passwordField).sendKeys(password);
        findElement(passwordConfirmField).sendKeys(password);
        pause(500L);
        findElement(organizationField).click();
        findElement(organizationField).sendKeys(organization);
        findElement(firstSearchResult).click();
        findElement(firstNameField).sendKeys(firstName);
        findElement(lastNameField).sendKeys(lastName);
        findElement(titleField).sendKeys(title);
        findElement(phoneField).sendKeys(phone);
        findElement(saveButton).click();

        return this;
    }

    public ProfileDetails selectFirstProfileInList() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        findElement(firstProfileInList).click();

        return new ProfileDetails(getDriver());
    }
}
