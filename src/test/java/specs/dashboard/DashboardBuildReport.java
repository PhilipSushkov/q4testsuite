package specs.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
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
    public void canCancelBuildReport() {
        new Dashboard(driver).selectBuildReport()
                .cancelBuildReportModal();

        Assert.assertEquals(0, driver.findElements(By.cssSelector(".report-create .cancel-button")).size());
    }

    @Test
    public void canDismissBuildReportModal() {
        new Dashboard(driver).selectBuildReport()
                .dismissBuildReportModal();

        Assert.assertEquals(0, driver.findElements(By.cssSelector(".report-create .cancel-button")).size());
    }
}
