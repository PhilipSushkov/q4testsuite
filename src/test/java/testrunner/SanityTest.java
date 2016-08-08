package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.loginPage.ForgotPassword;
import specs.loginPage.UserLogin;

/**
 * Created by patrickp on 2016-08-08.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserLogin.class,
        ForgotPassword.class
})
public class SanityTest {
}
