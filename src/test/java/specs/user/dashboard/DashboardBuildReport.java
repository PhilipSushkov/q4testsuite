package specs.user.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-08-10.
 */

public class DashboardBuildReport extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canCancelCreateBriefingBook() {
        new Dashboard(driver).selectCreateBriefingBook()
                .cancelCreateBriefingBookModal();

        Assert.assertEquals("Report modal is still visible", 0, driver.findElements(By.cssSelector(".report-create .cancel-button")).size());
    }

    @Test
    public void canDismissCreateBriefingBookModal() {
        new Dashboard(driver).selectCreateBriefingBook()
                .dismissCreateBriefingBookModal();

        Assert.assertEquals("Report modal is still visible", 0, driver.findElements(By.cssSelector(".report-create .cancel-button")).size());
    }
}
