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

    public ProfilesList(WebDriver driver) {
        super(driver);
    }

    public ProfilesList searchForProfile(String username) {
        findElement(profileSearchField).sendKeys(username);

        return this;
    }

    public String getProfileList() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        return findElement(profileList).getText();
    }
}
