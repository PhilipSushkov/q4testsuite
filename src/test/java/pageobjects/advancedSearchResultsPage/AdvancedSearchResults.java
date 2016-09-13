package pageobjects.advancedSearchResultsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-13.
 */
public class AdvancedSearchResults extends AbstractPageObject {
    private final By searchResults = By.cssSelector(".advanced-search-results");

    public AdvancedSearchResults(WebDriver driver) {
        super(driver);
    }

    public String getAdvancedSearchResults() {
        return findElement(searchResults).getText();
    }
}
