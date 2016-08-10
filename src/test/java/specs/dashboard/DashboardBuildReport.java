package specs.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.dashboardPage.Dashboard;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-08-10.
 */

public class DashboardBuildReport extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser("patrickp@q4inc.com", "patrick!");
    }

    @Test
    public void canDismissBuildReportModal() {
        String searchFieldText = "What are you looking for?";
        Dashboard start = new Dashboard(driver);

        start.selectBuildReport()
                .dismissBuildReportModal();

        Assert.assertEquals(searchFieldText, start.getSearchFieldText());
    }
}
