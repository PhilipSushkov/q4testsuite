package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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

public class NightRun {
}
