package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.activityPage.LogActivityPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-22.
 */
public class SearchForActivity extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    @Test
    public void canSearchForActivity() {
        String comment1 = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note1 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag1 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        String comment2 = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note2 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag2 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.logNote();
        new LogActivityPage(driver)
                .enterNoteDetails(comment1, note1, tag1)
                .accessSideNavFromPage()
                .selectDashboardFromSideNav();

        new Dashboard(driver).logNote();
        new LogActivityPage(driver)
                .enterNoteDetails(comment2, note2, tag2)
                .accessSideNavFromPage()
                .selectDashboardFromSideNav();
        new Dashboard(driver)
                .accessSideNav()
                .selectActivityPageFromSideNav()
                .searchForNote(comment2);

        Assert.assertThat("Searching did not return the expected results", activityPage.getNewNote(), containsString(comment2));
        Assert.assertNotEquals("Searching found more than expected", activityPage.getNewNote(), containsString(comment1));
    }

    @Test
    public void searchForActivityThatDoesNotExist() {
        String noResults = "No activities available.";
        ActivityPage activityPage = new ActivityPage(driver).searchForNote("123456789");

        Assert.assertThat("Searching did not return the expected results", activityPage.getNoNote(), containsString(noResults));
    }
}
