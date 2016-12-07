package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.dashboard.DashboardSearch;
import specs.user.login.ForgotPassword;
import specs.user.login.UserLogin;
import specs.user.research.ResearchList;
import specs.user.securityDetails.Overview;
import specs.user.sideNavBar.TabNavigationExpanded;
import specs.user.targeting.TargetingList;
import specs.user.team.Q4TeamPage;
import specs.user.watchlist.EditWatchlist;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        SuiteA.class,
        SuiteB.class,
        SuiteC.class,
        SuiteD.class,
        SuiteE.class
       /*
        Run two
        DashboardSearch.class,
        ForgotPassword.class,
        UserLogin.class,
        TabNavigationExpanded.class,
        EditWatchlist.class,
        Q4TeamPage.class,
        TargetingList.class,
        Overview.class,
        ResearchList.class*/
})

public class DesktopSuiteTwo {
}
