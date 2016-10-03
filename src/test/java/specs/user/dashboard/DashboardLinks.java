package specs.user.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.securityPage.SecurityOverviewPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-08-08.
 */
public class DashboardLinks extends AbstractSpec {

    @Before
    public void logInUser() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canNavigateToMyCompanyBigSharePrice() {
        String companyName = "Sysco Corp";
        SecurityOverviewPage finish = new SecurityOverviewPage(driver);
        new Dashboard(driver).clickBigSharePrice();

        Assert.assertEquals(companyName, finish.getCompanyName());
    }

    @Test
    public void canNavigateToMyCompanySmallSharePrice() {
        String companyName = "Sysco Corp";
        SecurityOverviewPage finish = new SecurityOverviewPage(driver);
        new Dashboard(driver).clickSmallSharePrice();

        Assert.assertEquals(companyName, finish.getCompanyName());
    }
}
