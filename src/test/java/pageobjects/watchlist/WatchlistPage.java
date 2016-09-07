package pageobjects.watchlist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.securityPage.SecurityOverviewPage;

/**
 * Created by patrickp on 2016-08-05.
 */
public class WatchlistPage extends AbstractPageObject{

    private final By editWatchlistButton = By.cssSelector(".toolbar-panel .toolbar-button");
    private final By securitySearchField = By.cssSelector(".toolbar-panel .search .x-field-input .x-input-el");
    private final By searchResult = By.cssSelector(".watchlist-search-results .x-list-item .icon.q4i-add-4pt");
    private final By watchlist = By.id("ext-component-7");
    private final By watchlistDeleteButton = By.cssSelector(".watch-list.done-mode .delete-btn");
    private final By deleteButton = By.cssSelector(".watch-list .watchlist-action-toolbar .x-button.delete");
    private final By firstCompanyInList = By.cssSelector(".watch-list .watchlist-row");
    private final By doneButton = By.cssSelector(".toolbar-panel.banner-toolbar .docked");

    public WatchlistPage(WebDriver driver) {
        super(driver);
    }

    public WatchlistPage addSecurityToWatchlist(String security) {
        wait.until(ExpectedConditions.elementToBeClickable(securitySearchField));
        findElement(securitySearchField).click();
        findElement(securitySearchField).sendKeys(security);
        wait.until(ExpectedConditions.elementToBeClickable(searchResult));
        findElement(searchResult).click();
        pause(500L);

        // Page refresh to make sure changes have been saved
        driver.navigate().refresh();

        return this;
    }

    public String getWatchlistSecurities() {
        waitForElementToAppear(watchlist);
        return findElement(watchlist).getText();
    }

    public WatchlistPage removeSecurityFromWatchlist() {
        // Removes first company from watchlist
        wait.until(ExpectedConditions.elementToBeClickable(editWatchlistButton));
        findElement(editWatchlistButton).click();
        pause(1000L);
        findElement(watchlistDeleteButton).click();
        pause(500L);
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        findElement(deleteButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(doneButton));
        findElement(doneButton).click();

        driver.navigate().refresh();

        return this;
    }

    public String getFirstCompanyInList() {
        return findElement(firstCompanyInList).getText();
    }

    public SecurityOverviewPage clickOnFirstWatchlistCompany() {
        findElement(firstCompanyInList).click();

        return new SecurityOverviewPage(getDriver());
    }
}
