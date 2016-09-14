package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.activity.LogActivity;
import specs.activity.SearchForActivity;
import specs.contacts.ContactDetails;
import specs.contacts.ContactList;
import specs.dashboard.DashboardBuildReport;
import specs.dashboard.DashboardLinks;
import specs.dashboard.DashboardLogActivity;
import specs.dashboard.DashboardSearch;
import specs.loginPage.ForgotPassword;
import specs.loginPage.UserLogin;
import specs.reportBuilder.CreateContactTearSheet;
import specs.sideNavBar.TabNavigationExpanded;
import specs.watchlist.EditWatchlist;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LogActivity.class,
        SearchForActivity.class,
        ContactList.class,
        ContactDetails.class,
        DashboardBuildReport.class,
        DashboardLinks.class,
        DashboardLogActivity.class,
        DashboardSearch.class,
        ForgotPassword.class,
        UserLogin.class,
        CreateContactTearSheet.class,
        TabNavigationExpanded.class,
        EditWatchlist.class,
})

public class NightRun {
}
