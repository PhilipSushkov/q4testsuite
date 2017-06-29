package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import specs.AbstractSpec;
import java.text.ParseException;

/**
 * Created by sarahr on 3/27/2017.
 */
public class ActivityDetails extends AbstractSpec {

    String title = "Activity Details Test " + RandomStringUtils.randomAlphanumeric(6);
    String location = "New York";
    String tag = "automation" + RandomStringUtils.randomAlphabetic(6);
    String newTag = "newTag" + RandomStringUtils.randomAlphabetic(6);

    @Before
    public void setup() {
        //Logging an activity
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
        new ActivityPage(driver).logNote().enterRoadshowDetails(title, location, tag).postActivity().accessSideNavFromPage().selectActivityPageFromSideNav();
    }

    @Test
    public void detailsPageAppears(){
        //checking to see if the word Details appears in the correct section
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver)
                .searchForNote(title)
                .selectFirstNoteInList();
        Assert.assertTrue(noteDetailsPage.detailsPageExists());
    }

    @Test
    public void titleIsCorrect(){
        //checking to see if the title on the activity page is the same on the details page
        NoteDetailsPage note = new NoteDetailsPage(driver)
                .searchForNote(title)
                .selectFirstNoteInList();
        String actualTitle = note.getActivityTitle();
        Assert.assertEquals("Titles do not match", actualTitle, title);
    }

    @Test
    public void locationIsCorrect(){
        //Checks if location in details page is the same as one displayed on activity page
        NoteDetailsPage note = new NoteDetailsPage(driver);

        note.searchForNote(title);
        String activityLocation = note.getActivityPageLocation();
        note.selectFirstNoteInList();

        String detailsLocation = note.getDetailsLocation();

        Assert.assertEquals("Locations do not match", detailsLocation, activityLocation);

    }

    @Test
    public void tagIsCorrect(){
        //Checking to see if the tag on the details page is the same as tag generated above
        NoteDetailsPage note = new NoteDetailsPage(driver)
                .searchForNote(title)
                .selectFirstNoteInList().addNewTag(tag);
        String actualTag = note.getDetailsTag();
        //Add '#' because the actual tag contains '#' in the beginning
        Assert.assertEquals("Tags do not match", actualTag, "#"+tag);

    }

    @Test
    public void dateIsCorrect() throws ParseException {
        //Checking to see if date in details page is the same as one displayed on activity page
        NoteDetailsPage note = new NoteDetailsPage(driver);

        note.searchForNote(title);
        String activityDate = new ActivityPage(driver).getDate();
        note.selectFirstNoteInList();

        String detailsDate = note.getDetailsDate();

        Assert.assertEquals("Dates do not match", activityDate, detailsDate);

    }

    @Ignore
    @Test
    public void canEditActivity(){

    }

    @Test
    public void canAddTag(){
        //Two parts; 1st part: Check if tag can be added from details page
        NoteDetailsPage note = new NoteDetailsPage(driver)
                .searchForNote(title)
                .selectFirstNoteInList()
                .addNewTag(newTag);
        note.pageRefresh();
        String actualNewTag = note.getDetailsTag();

        Assert.assertEquals("New tag does not match", actualNewTag, "#"+newTag);

        //2nd part: Check from the activity page if new tag is there
        note.goBackPages(1);
        note.searchForNote(title);
        String actualNewTagOnActivityPage = note.getActivityPageTag();

        Assert.assertEquals("New tag on activity page does not match", actualNewTagOnActivityPage, "#"+newTag);

    }

}

