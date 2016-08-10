package pageobjects.sideNavBar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.dashboardPage.Dashboard;
import pageobjects.watchlist.WatchlistPage;
import pageobjects.activityPage.ActivityPage;

/**
 * Created by patrickp on 2016-08-04.
 */
public class SideNavBar extends AbstractPageObject{

    private final By dashBoardSideNav = By.cssSelector(".x-treelist-q4-desktop-navigation .x-treelist-item-text");
    private final By watchlistSideNav = By.id("ext-treelistitem-2");
    private final By activityPageSideNav = By.id("ext-treelistitem-3");

    public SideNavBar(WebDriver driver) {
        super(driver);
    }


    public Dashboard selectDashboardFromSideNav() {
        pause(2000L);
        waitForElementToAppear(dashBoardSideNav);
        findElement(dashBoardSideNav).click();

        return new Dashboard(getDriver());
    }

    public WatchlistPage selectWatchListFromSideNav() {
        pause(2000L);
        waitForElementToAppear(watchlistSideNav);
        findElement(watchlistSideNav).click();

        return new WatchlistPage(getDriver());
    }

    public ActivityPage selectActivityPageFromSideNav() {
        pause(2000L);
        waitForElementToAppear(activityPageSideNav);
        findElement(activityPageSideNav).click();

        return new ActivityPage(getDriver());
    }
}
