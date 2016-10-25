package specs.user.dashboard;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-04.
 */
public class DashboardLogActivity extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canLogNoteFromDashboard() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new Dashboard(driver).logNote()
                .enterNoteDetails(comment, note, tag)
                .postActivity()
                .accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
        Assert.assertThat(activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogPhoneCallFromDashboard() {
        String comment = "This is a phone comment" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Joe" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a phone note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "PhoneTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new Dashboard(driver).logPhoneNote()
                .enterPhoneNoteDetails(comment, name, note, tag)
                .postActivity()
                .accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
        Assert.assertThat(activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogEmailFromDashboard() {
        String comment = "This is an email comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is an email noe" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "EmailTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new Dashboard(driver).logEmailNote()
                .enterEmailNoteDetails(comment, note, tag)
                .postActivity()
                .accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
        Assert.assertThat(activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogMeetingFromDashboard() {
        String comment = "This is a meeting comment" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Joe" + RandomStringUtils.randomAlphanumeric(5);
        String note = "This is a meeting note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "MeetingTag" + RandomStringUtils.randomAlphanumeric(3);

        ActivityPage activityPage = new ActivityPage(driver);
        new Dashboard(driver).logMeetingNote()
                .enterMeetingDetails(comment, name, note, tag)
                .postActivity()
                .accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
        Assert.assertThat(activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canCancelActivity() {
        new Dashboard(driver).logNote()
                .cancelNote();

        Assert.assertEquals(0, driver.findElements(By.cssSelector(".new-note .cancel-btn")).size());
    }

    @Test
    public void canLinkNoteToInstitution() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String institution = "Norfund";

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logNote()
                .linkNoteToInstitution(institution)
                .enterNoteDetails(comment, note, tag)
                .postActivity()
                .accessSideNav()
                .selectActivityPageFromSideNav()
                .selectFirstNoteInList();

        Assert.assertThat(noteDetailsPage.getNoteBody(), containsString(note));
        Assert.assertThat(noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat(noteDetailsPage.getLinkedToText(), containsString(institution));
    }

    @Test
    public void canLinkNoteToFund() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String fund = "Fundy";

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logNote()
                .linkNoteToFund(fund)
                .enterNoteDetails(comment, note, tag)
                .postActivity()
                .accessSideNav()
                .selectActivityPageFromSideNav()
                .selectFirstNoteInList();

        Assert.assertThat(noteDetailsPage.getNoteBody(), containsString(note));
        Assert.assertThat(noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat(noteDetailsPage.getLinkedToText(), containsString(fund));
    }

    @Test
    public void canLinkNoteToContact() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String contact = "Joe Galligan";

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logNote()
                .linkNoteToContact(contact)
                .enterNoteDetails(comment, note, tag)
                .postActivity()
                .accessSideNav()
                .selectActivityPageFromSideNav()
                .selectFirstNoteInList();

        Assert.assertThat(noteDetailsPage.getNoteBody(), containsString(note));
        Assert.assertThat(noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat(noteDetailsPage.getLinkedToText(), containsString(contact));
    }

    @Test
    public void canLinkNoteToCompany() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String company = "Sysco";

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logNote()
                .linkNoteToCompany(company)
                .enterNoteDetails(comment, note, tag)
                .postActivity()
                .accessSideNav()
                .selectActivityPageFromSideNav()
                .selectFirstNoteInList();

        Assert.assertThat(noteDetailsPage.getNoteBody(), containsString(note));
        Assert.assertThat(noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat(noteDetailsPage.getLinkedToText(), containsString(company));
    }
}
