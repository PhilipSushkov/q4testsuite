package specs.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.companyPage.CompanyOverviewPage;
import pageobjects.dashboardPage.Dashboard;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-08-08.
 */
public class DashboardLinksToMyCompany extends AbstractSpec {

    @Before
    public void logInUser() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canNavigateToMyCompanyBigSharePrice() {
        String companyName = "LinkedIn Corp.";
        CompanyOverviewPage finish = new CompanyOverviewPage(driver);
        new Dashboard(driver).clickBigSharePrice();

        Assert.assertEquals(companyName, finish.getCompanyName());
    }

    @Test
    public void canNavigateToMyCompanySmallSharePrice() {
        String companyName = "LinkedIn Corp.";
        CompanyOverviewPage finish = new CompanyOverviewPage(driver);
        new Dashboard(driver).clickSmallSharePrice();

        Assert.assertEquals(companyName, finish.getCompanyName());
    }
}
