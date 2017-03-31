package specs.user.estimates;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.Calendar;
import pageobjects.user.estimatesPage.SecurityEstimatesPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by kelvint on 11/2/16.
 */
public class Estimates extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectEstimatesFromSideNav();
    }

    //Test for the research section at the bottom of the page
    @Test
    public void canSortResearchByDate() {
        // Checking all research items fall within a designated time frame selected through "start" and "end" times
        SecurityEstimatesPage estimatesPage = new SecurityEstimatesPage(driver);
        Calendar calendar = new Calendar(driver);
        estimatesPage.filterDate(calendar);

        Assert.assertTrue("Research Reports cannot be sorted by date.", new SecurityEstimatesPage(driver).sortByDateRange(calendar));
    }

}
