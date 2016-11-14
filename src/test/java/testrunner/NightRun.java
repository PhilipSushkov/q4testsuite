package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.research.ResearchList;
import specs.user.securityDetails.Overview;
import specs.user.activity.LogActivity;
import specs.user.activity.SearchForActivity;
import specs.user.contacts.ContactDetails;
import specs.user.contacts.ContactList;
import specs.user.dashboard.DashboardBuildReport;
import specs.user.dashboard.DashboardLinks;
import specs.user.dashboard.DashboardLogActivity;
import specs.user.dashboard.DashboardSearch;
import specs.user.login.ForgotPassword;
import specs.user.login.UserLogin;
import specs.user.reportBuilder.CreateContactTearSheet;
import specs.user.sideNavBar.TabNavigationExpanded;
import specs.user.targeting.TargetingList;
import specs.user.team.Q4TeamPage;
import specs.user.watchlist.EditWatchlist;

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
        Q4TeamPage.class,
        TargetingList.class,
        Overview.class,
        ResearchList.class
})

public class NightRun {
}
