package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

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
    public void canLogNoteFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        int count = new ActivityPage(driver).getNoteCount();

        System.out.println(new ActivityPage(driver).getNoteCount());

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()

                .enterNoteDetails(comment, note, tag);

        // Make sure the new comment appears on page
        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
        // Make sure the note count increased by 1
        Assert.assertThat(activityPage.getNoteCount(), (equalTo((count + 1 ))));
    }

    @Test
    public void canLogCallFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Person" + RandomStringUtils.randomAlphanumeric(3);
        String phoneNumber = RandomStringUtils.randomNumeric(7);
        String city = "Toronto";

        int count = new ActivityPage(driver).getCallCount();

        System.out.println(new ActivityPage(driver).getCallCount());

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()
                .choosePhoneTab()
                .enterPhoneNoteDetails(comment, phoneNumber, name, city, note, tag);

        // Make sure the new comment appears on page
        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
        // Make sure the note count increased by 1
        Assert.assertThat(activityPage.getCallCount(), (equalTo((count + 1 ))));
    }

    @Test
    public void canLogEmailFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String email = "person@" + RandomStringUtils.randomAlphanumeric(4) + ".com";

        int count = new ActivityPage(driver).getEmailCount();

        System.out.println(new ActivityPage(driver).getEmailCount());

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()
                .chooseEmailTab()
                .enterEmailNoteDetails(comment, email, note, tag);

        // Make sure the new comment appears on page
        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
        // Make sure the note count increased by 1
        Assert.assertThat(activityPage.getEmailCount(), (equalTo((count + 1 ))));
    }

    @Test
    public void canLogMeetingFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Person" + RandomStringUtils.randomAlphanumeric(3);
        String phoneNumber = RandomStringUtils.randomNumeric(7);
        String city = "Toronto";

        int count = new ActivityPage(driver).getMeetingCount();

        System.out.println(new ActivityPage(driver).getMeetingCount());

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()
                .chooseMeetingTab()
                .enterMeetingDetails(comment, phoneNumber, name, city, note, tag);

        // Make sure the new comment appears on page
        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
        // Make sure the note count increased by 1
        Assert.assertThat(activityPage.getMeetingCount(), (equalTo((count + 1 ))));
    }
}
