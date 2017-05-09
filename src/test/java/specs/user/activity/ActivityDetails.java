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

    @Before
    public void setup() {
        //Logging an activity
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
        new ActivityPage(driver).logNote().enterRoadshowDetails(title, location, tag).postActivity();
    }

    @Test
    public void detailsPageAppears(){
        //checking to see if the word Details appears in the correct section
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).selectFirstNoteInList();
        Assert.assertTrue(noteDetailsPage.detailsPageExists());
    }

    @Test
    public void titleIsCorrect(){
        //checking to see if the title on the activity page is the same on the details page
        NoteDetailsPage note = new NoteDetailsPage(driver).selectFirstNoteInList();
        String actualTitle = note.getActivityTitle();
        Assert.assertEquals("Title's do not match", actualTitle, title);
    }

    @Ignore
    @Test
    public void locationIsCorrect(){

    }

    @Test
    public void tagIsCorrect(){
        //Checking to see if the tag on the details page is the same as tag generated above
        NoteDetailsPage note = new NoteDetailsPage(driver).selectFirstNoteInList();
        String actualTag = note.getDetailsTag();
        //Add '#' because the actual tag contains '#' in the beginning
        Assert.assertEquals("Tags do not match", actualTag, "#"+tag);

    }

    @Test
    public void dateIsCorrect() throws ParseException {
        //Checking to see if the date on the activity page is equal to date on details page
        NoteDetailsPage note = new NoteDetailsPage(driver);
        String activityDate = note.getDate();

        note.selectFirstNoteInList();
        String detailsDate = note.getDetailsDate();
        Assert.assertEquals("Dates do not match", activityDate, detailsDate);

    }

    @Ignore
    @Test
    public void canEditActivity(){

    }

    @Ignore
    @Test
    public void canAddTag(){

    }

}

