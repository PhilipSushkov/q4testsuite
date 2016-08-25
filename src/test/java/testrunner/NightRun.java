package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.activity.LogActivityFromActivityPage;
import specs.activity.SearchForActivity;
import specs.contacts.AddRemoveContacts;
import specs.dashboard.DashboardBuildReport;
import specs.dashboard.DashboardLogActivity;
import specs.dashboard.DashboardLinksToMyCompany;
import specs.dashboard.DashboardSearch;
import specs.loginPage.ForgotPassword;
import specs.loginPage.UserLogin;
import specs.sideNavBar.TabNavigationExpanded;
import specs.watchlist.EditWatchlist;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DashboardSearch.class,
        UserLogin.class,
        TabNavigationExpanded.class,
        ForgotPassword.class,
        DashboardLinksToMyCompany.class,
        DashboardLogActivity.class,
        DashboardBuildReport.class,
        SearchForActivity.class,
        LogActivityFromActivityPage.class,
        AddRemoveContacts.class,
        EditWatchlist.class
})

public class NightRun {
}
