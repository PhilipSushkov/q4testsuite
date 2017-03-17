package pageobjects.user.advancedSearchPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-13.
 */
public class AdvancedSearchPage extends AbstractPageObject {
    private final By searchResults = By.cssSelector(".advanced-search-results .advanced-search-results-inner");
    private final By searchField = By.cssSelector(".advanced-search-header .search-field .x-input-search");
    private final By transcriptsTab = By.cssSelector(".q4i-transcripts-2pt");
    private final By researchTab = By.cssSelector(".advanced-search .search-bar .search-tabs .x-button:last-of-type, .advanced-search .search-bar .search-tabs .x-button:last-child");

    public AdvancedSearchPage(WebDriver driver) {
        super(driver);
    }

    public String getAdvancedSearchResults() {
        waitForLoadingScreen();
        return findElement(searchResults).getText();
    }

    public AdvancedSearchPage advancedSearchFor(String entity) {
        waitForLoadingScreen();
        findElement(searchField).click();
        findElement(searchField).sendKeys(entity);
        findElement(searchField).sendKeys(Keys.ENTER);
        waitForLoadingScreen();

        return this;
    }

    public AdvancedSearchPage selectTranscriptsTab() {
        findElement(transcriptsTab).click();

        return this;
    }

    public AdvancedSearchPage selectResearchTab() {
        findElement(researchTab).click();
        waitForLoadingScreen();

        return this;
    }
}
