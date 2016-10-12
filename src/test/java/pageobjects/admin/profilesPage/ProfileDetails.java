package pageobjects.admin.profilesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-10-11.
 */
public class ProfileDetails extends AbstractPageObject{
    private final By editProfileButton = By.cssSelector(".page-header .action-buttons .edit");
    private final By firstNameField = By.name("firstName");
    private final By saveButton = By.cssSelector(".button.button-yellow");
    private final By loadingSpinner = By.className("outer-spinner-container");

    public ProfileDetails(WebDriver driver) {
        super(driver);
    }

    public ProfileDetails editProfileFirstName(String firstName) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        findElement(editProfileButton).click();
        findElement(firstNameField).clear();
        findElement(firstNameField).sendKeys(firstName);
        findElement(saveButton).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));

        return this;
    }
}
