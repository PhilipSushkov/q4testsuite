package specs.user.Security;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;

/**
 * Created by kelvint on 11/2/16.
 */
public class Overview extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectSecurityFromSideNav();
    }

    @Test

    /**   Combines Test Cases C490 & C491    */

    public void companyDataCorrect()
    {
        boolean dataExists = true;
        SecurityOverviewPage test = new SecurityOverviewPage(driver);

        if(test.getCompanyName() != "Sysco Corp")
            dataExists = false;
        else if(test.getCompanyTicker() != "SYY")
            dataExists = false;
        else if(test.getIndustry_Exchange() != "Food Distributors NYSE")
            dataExists = false;

        Assert.assertTrue(dataExists);
    }

    @After

    public void disableDriver()
    {
        driver.close();
        driver.quit();
    }

}
