package specs.sideNavBar;

import org.junit.Before;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-08-05.
 */
public class TabNavigationCollapsed extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
    }
}
