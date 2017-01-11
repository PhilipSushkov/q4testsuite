package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.activity.FilterActivity;
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
import specs.user.reportBuilder.CreateBriefingBook;
import specs.user.reportBuilder.EditBriefingBook;
import specs.user.research.ResearchList;
import specs.user.securityDetails.Overview;
import specs.user.securityDetails.Ownership;
import specs.user.sideNavBar.TabNavigationExpanded;
import specs.user.targeting.TargetingList;
import specs.user.team.Q4TeamPage;
import specs.user.watchlist.EditWatchlist;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LogActivity.class,
        FilterActivity.class,
        ContactList.class,
        ContactDetails.class,
        DashboardBuildReport.class,
        DashboardLinks.class,
        DashboardLogActivity.class,
        DashboardSearch.class,
        ForgotPassword.class,
        UserLogin.class,
        Overview.class,
        TabNavigationExpanded.class,
        EditWatchlist.class,
        Q4TeamPage.class,
        SearchForActivity.class,
        TargetingList.class,
        ResearchList.class,
        Ownership.class,
        CreateBriefingBook.class,
        EditBriefingBook.class
})

public class NightRun {
}
