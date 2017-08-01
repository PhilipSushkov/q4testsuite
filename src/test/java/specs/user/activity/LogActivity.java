package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-22.
 */
public class LogActivity extends AbstractSpec {
    public final static String keyword = "**AUTOMATION** ";

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
    //Really weird behaviour, .getNewNote returns wrong data
    public void createNoteWith$Symbole(){
        String comment = keyword+"This is a test comment with $" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note with $" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new ActivityPage(driver).logNote()
                .enterNoteDetails(comment, note, tag);

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", noteDetailsPage.getCommentText(), containsString(comment));
    }

    @Test
    public void canLogNoteFromActivityPage() {
        String comment =keyword+"This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new ActivityPage(driver).logNote()
                .enterNoteDetails(comment, note, tag);

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", noteDetailsPage.getCommentText(), containsString(comment));
    }

    @Test
    public void canLogCallFromActivityPage() {
        String comment =keyword+"This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Person" + RandomStringUtils.randomAlphanumeric(3);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new ActivityPage(driver).logNote()
                .enterPhoneNoteDetails(comment, name, note, tag)
                .postPhone();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", noteDetailsPage.getCommentText(), containsString(comment));
    }

    @Test
    public void canLogEmailFromActivityPage() {
        String comment =keyword+"This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new ActivityPage(driver).logNote()
                .enterEmailNoteDetails(comment, note, tag)
                .postEmail();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", noteDetailsPage.getCommentText(), containsString(comment));
    }

    @Test
    public void canLogMeetingFromActivityPage() {
        String comment =keyword+"This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String name = "Person" + RandomStringUtils.randomAlphanumeric(3);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new ActivityPage(driver).logNote()
                .enterMeetingDetails(comment, name, note, tag)
                .postMeeting();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", noteDetailsPage.getCommentText(), containsString(comment));
    }

    @Test
    public void canLogRoadShowFromActivityPage() {
        String title =keyword+"This is a test title" + RandomStringUtils.randomAlphanumeric(6);
        String location = "This is a test location" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new ActivityPage(driver).logNote()
                .enterRoadshowDetails(title, location, tag)
                .postRoadshow();

        // Make sure the new comment appears on page
        Assert.assertThat("Note text does not match expected", noteDetailsPage.getLocation(), containsString(location));
    }
}