package specs.admin.profiles;

import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.profilesPage.ProfilesList;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityEstimatesPage;
import specs.AdminAbstractSpec;

/**
 * Created by patrickp on 2016-10-13.
 */
public class EnableDisableSubscriptions extends AdminAbstractSpec {

    @Before
    public void setUp() {
        new AdminLoginPage(driver).loginAdmin()
                .navigateToProfilesPage();
    }

    //This still needs an assert
    @Test
    public void canDisableEstimates() {
        new ProfilesList(driver).searchForProfile("Patrick")
                .selectFirstProfileInList()
                .disableEstimates()

                .enableResearch()
                .enabledExpectedRanges()
                .enableSentiment()
                .enabledVolatility()
                .enabledActivism()
                .enabledEvents()
                .enableWebcast()
                .enabledWebsite()
                .enabledTradingAnalytics()
                .enableRelativePerformance()
                .enableSurveillance();

        driver.navigate().to("https://develop.q4desktop.com");

        SecurityEstimatesPage loginPage = new LoginPage(driver).customLoginUser("patrickp@q4inc.com", "patrick!")
                .accessSideNav()
                .selectEstimatesFromSideNav();
    }
}
