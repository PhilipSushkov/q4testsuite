package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.dashboard.DashboardBuildReport;
import specs.dashboard.DashboardLogActivity;
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
        DashboardLogActivity.class,
        DashboardBuildReport.class
})

public class NightRun {
}
