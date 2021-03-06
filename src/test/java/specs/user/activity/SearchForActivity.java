package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.activityPage.LogActivityPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-22.
 */
public class SearchForActivity extends AbstractSpec {
    public final static String keyword = "**AUTOMATION**";
    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }
    @After
    public void cleanUp(){
        try {
            NoteDetailsPage note = new NoteDetailsPage(driver);
            ActivityPage activity = note.accessSideNavFromPage().selectActivityPageFromSideNav();
            activity.deleteAllNotes(keyword);
            activity.waitForLoadingScreen();
        }
        catch(Exception e){
            //I don't want tests to fail because the clean up failed
        }
    }

    @Test
    public void canSearchForActivity() {
        String comment1 = "**AUTOMATION** This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note1 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag1 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        String comment2 = "**AUTOMATION** This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note2 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag2 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.logNote();
        new LogActivityPage(driver)
                .enterNoteDetails(comment1, note1, tag1)
                .accessSideNavFromPage()
                .selectDashboardFromSideNav();

        new Dashboard(driver).logActivity();
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
