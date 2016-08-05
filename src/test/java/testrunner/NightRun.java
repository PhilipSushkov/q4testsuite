package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.dashboard.DashboardSearch;
import specs.sideNavBar.TabNavigationExpanded;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DashboardSearch.class,
        TabNavigationExpanded.class
})

public class NightRun {
}
