package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.login.ForgotPassword;
import specs.user.login.UserLogin;

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
