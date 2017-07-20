package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.dashboard.DashboardSearch;
import specs.user.login.ForgotPassword;
import specs.user.login.UserLogin;
import specs.user.securityDetails.Overview;

/**
 * Created by noelc on 2016-12-07.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DashboardSearch.class,
        ForgotPassword.class,
        UserLogin.class,
        Overview.class,

})
public class SuiteC {
}
