package pageobjects.user.advancedSearchResultsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-13.
 */
public class AdvancedSearchResults extends AbstractPageObject {
    private final By searchResults = By.id("ext-tag-search-results-1");

    public AdvancedSearchResults(WebDriver driver) {
        super(driver);
    }

    public String getAdvancedSearchResults() {
        waitForLoadingScreen();
        return findElement(searchResults).getText();
    }
}
