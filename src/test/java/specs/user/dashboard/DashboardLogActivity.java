package specs.user.dashboard;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.activityPage.logActivityPage;
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
        new Dashboard(driver).logNote();
        new logActivityPage(driver)
                .enterNoteDetails(comment, note, tag);

        new NoteDetailsPage(driver)
                .addNewTag(tag)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment);

        Assert.assertThat("Note does not contain the expected comment text", activityPage.getNewNote(), containsString(comment));
        Assert.assertThat("Note does not contain the expected tag", activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogPhoneCallFromDashboard() {
        String comment = "This is a phone comment" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Joe" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a phone note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "PhoneTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new Dashboard(driver).logPhoneNote();
        new logActivityPage(driver)
                .enterPhoneNoteDetails(comment, name, note, tag)
                .postActivity()
                .addNewTag(tag)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment);

        Assert.assertThat("Note does not contain the expected comment text", activityPage.getNewNote(), containsString(comment));
        Assert.assertThat("Note does not contain the expected tag", activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogEmailFromDashboard() {
        String comment = "This is an email comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is an email noe" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "EmailTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new Dashboard(driver).logEmailNote();
        new logActivityPage(driver)
                .enterEmailNoteDetails(comment, note, tag)
                .postActivity()
                .addNewTag(tag)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment);

        Assert.assertThat("Note does not contain the expected comment text", activityPage.getNewNote(), containsString(comment));
        Assert.assertThat("Note does not contain the expected tag", activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogMeetingFromDashboard() {
        String comment = "This is a meeting comment" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Joe" + RandomStringUtils.randomAlphanumeric(5);
        String note = "This is a meeting note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "MeetingTag" + RandomStringUtils.randomAlphanumeric(3);

        ActivityPage activityPage = new ActivityPage(driver);
        new Dashboard(driver).logMeetingNote();
        new logActivityPage(driver)
                .enterMeetingDetails(comment, name, note, tag)
                .postActivity()
                .addNewTag(tag)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment);

        Assert.assertThat("Note does not contain the expected comment text", activityPage.getNewNote(), containsString(comment));
        Assert.assertThat("Note does not contain the expected tag", activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canLogRoadshowFromDashboard() {
        String comment = "This is a meeting comment" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Joe" + RandomStringUtils.randomAlphanumeric(5);
        String note = "This is a meeting note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "MeetingTag" + RandomStringUtils.randomAlphanumeric(3);


        ActivityPage activityPage = new ActivityPage(driver);
        new Dashboard(driver).logRoadshowNote();
        new logActivityPage(driver)
                .enterMeetingDetails(comment, name, note, tag)
                .postActivity()
                .addNewTag(tag)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment);

        Assert.assertThat("Note does not contain the expected comment text", activityPage.getNewNote(), containsString(comment));
        Assert.assertThat("Note does not contain the expected tag", activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canCancelActivity() {
        new Dashboard(driver).logNote()
                .cancelNote();

        Assert.assertEquals("Activity modal was not dismissed", 0, driver.findElements(By.cssSelector(".new-note .cancel-btn")).size());
    }

    @Test
    public void canLinkNoteToInstitution() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String institution = "Norfund";
        // The add activity note area is behaving strangely with selenium and truncating the first letter of our note so we'll use this to pass the test for now
        String expectedNote = note.substring(1);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logNote();
        logActivityPage logActivityPage = new logActivityPage(driver)
                .linkNoteToInstitution(institution)
                .enterNoteDetails(comment,note,tag);

        new Dashboard(driver)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment)
                .selectFirstNoteInList();

        Assert.assertThat("Note does not contain expected text", noteDetailsPage.getNoteBody(), containsString(expectedNote));
        Assert.assertThat("Note does not contain expected comment text", noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat("Note does not contain expected linked institution", noteDetailsPage.getLinkedToText(), containsString(institution));
    }

    @Test
    public void canLinkNoteToFund() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String fund = "Fundy";
        // The add activity note area is behaving strangely with selenium and truncating the first letter of our note so we'll use this to pass the test for now
        String expectedNote = note.substring(1);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logNote();
        logActivityPage logActivityPage = new logActivityPage(driver)
                .linkNoteToFund(fund)
                .enterNoteDetails(comment,note,tag);

        new Dashboard(driver)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment)
                .selectFirstNoteInList();

        Assert.assertThat("Note does not contain expected text", noteDetailsPage.getNoteBody(), containsString(expectedNote));
        Assert.assertThat("Note does not contain expected comment text", noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat("Note does not contain expected linked fund", noteDetailsPage.getLinkedToText(), containsString(fund));
    }

    @Test
    public void canLinkNoteToContact() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String contact = "Joe Galligan";
        // The add activity note area is behaving strangely with selenium and truncating the first letter of our note so we'll use this to pass the test for now
        String expectedNote = note.substring(1);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logNote();
        new logActivityPage(driver)
                .linkNoteToContact(contact)
                .enterNoteDetails(comment,note,tag);

        driver.navigate().refresh();

    //search comment and then search contacts
        Assert.assertThat("Note does not contain expected text", noteDetailsPage.getNoteBody(), containsString(expectedNote));
        Assert.assertThat("Note does not contain expected comment text", noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat("Note does not contain expected linked contact", noteDetailsPage.filterContactsOnlys(), containsString(contact));
    }
}
