package specs.user.securityDetails;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOwnershipPage;
import specs.AbstractSpec;

/**
 * Created by kelvint on 11/2/16.
 */
public class Ownership extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectOwnershipFromSideNav();
    }

    @Test
    public void dateOptionsWorkCorrectly(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("The displayed date options are incorrect.", securityOwnershipPage.dateOptionsAreValid());
        Assert.assertTrue("As of statement is incorrect when switching through date options.", securityOwnershipPage.asOfDateIsValid());
    }

}
