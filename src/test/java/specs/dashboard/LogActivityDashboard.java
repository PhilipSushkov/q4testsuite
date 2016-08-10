package specs.dashboard;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.activityPage.ActivityPage;
import pageobjects.dashboard.Dashboard;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-04.
 */
public class LogActivityDashboard extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser("patrickp@q4inc.com", "patrick!");
    }

    @Test
    public void canLogNoteFromDashboard() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage finish = new ActivityPage(driver);
        new Dashboard(driver).logNote()
                .enterNoteDetails(comment, note, tag);

        new Dashboard(driver).accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertThat(finish.getNewNote(), containsString(comment));
        Assert.assertThat(finish.getNewNote(), containsString(note));
        Assert.assertThat(finish.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogPhoneCallFromDashboard() {
        String comment = "This is a phone comment" + RandomStringUtils.randomAlphanumeric(6);
        String phone = RandomStringUtils.randomNumeric(10);
        String name = "Joe" + RandomStringUtils.randomAlphanumeric(6);
        String city = "Toronto" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This ia a phone note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "PhoneTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage finish = new ActivityPage(driver);
        new Dashboard(driver).logPhoneNote()
                .enterPhoneNoteDetails(comment, phone, name, city, note, tag);

        new Dashboard(driver).accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertThat(finish.getNewNote(), containsString(comment));
        Assert.assertThat(finish.getNewNote(), containsString(note));
        Assert.assertThat(finish.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogEmailFromDashboard() {
        String comment = "This is an email comment" + RandomStringUtils.randomAlphanumeric(6);
        String email = RandomStringUtils.randomAlphanumeric(6) + "@mail.com";
        String note = "This is an email noe" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "EmailTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage finish = new ActivityPage(driver);
        new Dashboard(driver).logEmailNote()
                .enterEmailNoteDetails(comment, email, note, tag);

        new Dashboard(driver).accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertThat(finish.getNewNote(), containsString(comment));
        Assert.assertThat(finish.getNewNote(), containsString(note));
        Assert.assertThat(finish.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogMeetingFromDashboard() {
        String comment = "This is a meeting comment" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Joe" + RandomStringUtils.randomAlphanumeric(5);
        String city = "Toronto" + RandomStringUtils.randomAlphanumeric(6);
        String state = "Ontario" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a meeting note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "MeetingTag" + RandomStringUtils.randomAlphanumeric(3);

        ActivityPage finish = new ActivityPage(driver);
        new Dashboard(driver).logMeetingNote()
                .enterMeetingDetails(comment, name, city, state, note, tag);

        new Dashboard(driver).accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertThat(finish.getNewNote(), containsString(comment));
        Assert.assertThat(finish.getNewNote(), containsString(note));
        Assert.assertThat(finish.getNewNote(), containsString(tag));
    }
}
// TODO Patrick -- Add linking tests for each note and link type
