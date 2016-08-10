package specs.sideNavBar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.dashboardPage.Dashboard;
import pageobjects.loginPage.LoginPage;
import pageobjects.watchlist.WatchlistPage;
import pageobjects.activityPage.ActivityPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-08-04.
 */
public class TabNavigationExpanded extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser("patrickp@q4inc.com", "patrick!");
    }


    @Test
     public void canNavigateToDashboard() {
        String searchFieldText = "What are you looking for?";
        Dashboard start = new Dashboard(driver);
        start.accessSideNav()
                .selectDashboardFromSideNav();

        Assert.assertEquals(searchFieldText, start.getSearchFieldText());
    }


    @Test
    public void canNavigateToWatchlist() {
        String pageTitle = "Watchlist";
        WatchlistPage finish = new WatchlistPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectWatchListFromSideNav();

        Assert.assertEquals(pageTitle, finish.getWatchlistPageTitle());
    }


    @Test
    public void canNavigateToActivity() {
        String pageTitle = "Activity";
        ActivityPage finish = new ActivityPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertEquals(pageTitle, finish.getActivityPageTitle());
    }

}
