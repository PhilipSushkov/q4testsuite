package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-22.
 */
public class LogActivity extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    @Test
    public void createNoteWith$Symbole(){
        String comment = "This is a test comment with $" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note with $" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()

                .enterNoteDetails(comment, note, tag)
                .postActivity();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", activityPage.getNewNote(), containsString(comment));
    }

    @Test
    public void canLogNoteFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()

                .enterNoteDetails(comment, note, tag)
                .postActivity();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", activityPage.getNewNote(), containsString(comment));
    }

    @Test
    public void canLogCallFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Person" + RandomStringUtils.randomAlphanumeric(3);


        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()
                .enterPhoneNoteDetails(comment, name, note, tag)
                .choosePhoneTab();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", activityPage.getNewNote(), containsString(comment));
    }

    @Test
    public void canLogEmailFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()
                .enterEmailNoteDetails(comment, note, tag)
                .chooseEmailTab();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", activityPage.getNewNote(), containsString(comment));
    }

    @Test
    public void canLogMeetingFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Person" + RandomStringUtils.randomAlphanumeric(3);

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()
                .enterMeetingDetails(comment, name, note, tag)
                .chooseMeetingTab();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", activityPage.getNewNote(), containsString(comment));
    }

    @Test
    public void canLogRoadShowFromActivityPage() {
        String title = "This is a test title" + RandomStringUtils.randomAlphanumeric(6);
        String location = "This is a test location" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()
                .enterRoadshowDetails(title, location, tag)
                .chooseRoadshowTab();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", activityPage.getNewNote(), containsString(title));
    }
}
