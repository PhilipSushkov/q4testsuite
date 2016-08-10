package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.dashboard.LogActivityDashboard;
import specs.dashboard.DashboardLinksToMyCompany;
import specs.dashboard.DashboardSearch;
import specs.loginPage.ForgotPassword;
import specs.loginPage.UserLogin;
import specs.sideNavBar.TabNavigationExpanded;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DashboardSearch.class,
        UserLogin.class,
        TabNavigationExpanded.class,
        ForgotPassword.class,
        DashboardLinksToMyCompany.class,
        LogActivityDashboard.class
})

public class NightRun {
}
