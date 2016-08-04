package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.Dashboard.DashboardSearch;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DashboardSearch.class
})

public class SmokeTest {
}
