package pageobjects.sideNavBar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.dashboard.Dashboard;
import pageobjects.watchlist.Watchlist;

/**
 * Created by patrickp on 2016-08-04.
 */
public class SideNavBar extends AbstractPageObject{

    private final By dashBoardSideNav = By.cssSelector(".x-treelist-q4-desktop-navigation .x-treelist-item-text");
    private final By watchlistSideNav = By.id("ext-treelistitem-2");

    public SideNavBar(WebDriver driver) {
        super(driver);
    }


    public Dashboard selectDashboardFromSideNav() {
        pause(2000L);
        waitForElementToAppear(dashBoardSideNav);
        findElement(dashBoardSideNav).click();

        return new Dashboard(getDriver());
    }

    public Watchlist selectWatchListFromSideNav() {
        pause(2000L);
        waitForElementToAppear(watchlistSideNav);
        findElement(watchlistSideNav).click();

        return new Watchlist(getDriver());
    }
}
