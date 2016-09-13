package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.activity.LogActivityFromActivityPage;
import specs.activity.SearchForActivity;
import specs.contacts.ContactList;
import specs.contacts.ContactDetails;
import specs.dashboard.DashboardBuildReport;
import specs.dashboard.DashboardLinksToMyCompany;
import specs.dashboard.DashboardLogActivity;
import specs.dashboard.DashboardSearch;
import specs.loginPage.ForgotPassword;
import specs.loginPage.UserLogin;
import specs.sideNavBar.TabNavigationExpanded;
import specs.watchlist.EditWatchlist;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LogActivityFromActivityPage.class,
        SearchForActivity.class,
        ContactList.class,
        ContactDetails.class,
        DashboardBuildReport.class,
        DashboardLinksToMyCompany.class,
        DashboardLogActivity.class,
        DashboardSearch.class,
        ForgotPassword.class,
        UserLogin.class,
        TabNavigationExpanded.class,
        EditWatchlist.class
})

public class NightRun {
}
