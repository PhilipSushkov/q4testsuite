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

    @Ignore
    @Test
    public void tagIsCorrect(){

    }

    @Ignore
    @Test
    public void dateIsCorrect(){

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

