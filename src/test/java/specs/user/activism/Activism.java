package specs.user.activism;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.activismPage.ActivismPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by kelvint on 11/2/16.
 */
public class Activism extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectSecurityFromSideNav()
                .navigateToActivismPage();
    }

    @Test
    public void checkForActivismNLG() {
        ActivismPage activismPage = new ActivismPage(driver);

        Assert.assertTrue("NLG is not present on page", activismPage.verifyTextIsPresent());
    }
}
