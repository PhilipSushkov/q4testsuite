package specs.watchlist;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.securityPage.SecurityOverviewPage;
import pageobjects.loginPage.LoginPage;
import pageobjects.watchlist.WatchlistPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;


/**
 * Created by patrickp on 2016-08-22.
 */
public class EditWatchlist extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectWatchListFromSideNav();
    }

    @Test
    public void canAddCompanyToWatchlist() {
        String security = "Apple, Inc.";
        WatchlistPage watchlist = new WatchlistPage(driver).removeSecurityFromWatchlist()
                .addSecurityToWatchlist(security);

        Assert.assertThat(watchlist.getWatchlistSecurities(), containsString(security));
    }

    @Test
    public void canRemoveCompanyFromWatchlist() {
        // Get first company from list and store as a string
        String companyName = new WatchlistPage(driver).getFirstCompanyInList();
        System.out.println(new WatchlistPage(driver).getFirstCompanyInList());

        WatchlistPage watchlistPage = new WatchlistPage(driver).removeSecurityFromWatchlist();

        // Compare stored string to first company in list. They shouldn't match
        System.out.println(new WatchlistPage(driver).getFirstCompanyInList());
        Assert.assertThat(watchlistPage.getFirstCompanyInList(), is(not(companyName)));
    }

    @Test
    public void canNavigateToCompanyFromWatchlist() {
        // Get first company from list and store as a string
        String companyName = new WatchlistPage(driver).getFirstCompanyInList();
        System.out.println(companyName);

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver).clickOnFirstWatchlistCompany();

        Assert.assertThat(companyName, containsString(securityOverviewPage.getCompanyName()));
    }
}