package specs.user.dashboard;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.user.briefingBooks.BriefingBookList;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

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

    @Test
    public void canCreateBriefingBookFromDashboard() {
        String briefingBookTitle = "Dashboard Briefing Book" + RandomStringUtils.randomAlphanumeric(3);
        BriefingBookList briefingBookList = new Dashboard(driver).selectCreateBriefingBook()
                .saveBriefingBook(briefingBookTitle)
                .accessSideNav()
                .selectBriefingBookFromSideNav();

        Assert.assertThat("Newly created briefing book is not visible", briefingBookList.getBriefingBookList(), containsString(briefingBookTitle));
    }
}
