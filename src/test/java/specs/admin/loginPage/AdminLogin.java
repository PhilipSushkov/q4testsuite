package specs.admin.loginPage;

import org.junit.Test;
import pageobjects.admin.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-09-16.
 */
public class AdminLogin extends AbstractSpec {

    @Test
    public void adminCanLogin() {
        LoginPage adminLogin = new LoginPage(driver).loginAdmin();
    }
}
