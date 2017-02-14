package specs.user.header;

import com.applitools.shaded.eyessdk.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.*;
import org.openqa.selenium.By;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;
import java.security.acl.Owner;

/**
 * Created by sarahr on 2/9/2017.
 */
public class header extends AbstractSpec{

    //no need for a before
    String releaseNotes = "https://develop.q4desktop.com/#release-note";

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectSecurityFromSideNav();
    }

    @Test
    public void canViewReleaseNotes(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();
        securityOverviewPage.clickReleaseNotes();

        String actualURL = driver.getCurrentUrl();

        Assert.assertEquals("This is not the release notes page", releaseNotes, actualURL);

    }

    @Test
    public void cannotLeaveBlankFeedback(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        Assert.assertTrue("Error message did not appear/Feedback was incorrectly left", securityOverviewPage.leaveBlankFeedback());
    }

    @Test
    public void canCancelLeaveFeedback(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        String feedbackMessage = "This is a QA test";

        Assert.assertTrue("Feedback box is still visible", securityOverviewPage.leaveFeedback(feedbackMessage));
    }

    //Changing password test cases

    @Test
    public void mustTypeCurrentPass(){
        //this message will appear if there is nothing written in the Current Password field
        //regardless of what is written in the other two fields

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        String newPass = "q4p@ss123";

        Assert.assertTrue("Error message did not appear", securityOverviewPage.noCurrentPassword(newPass));
    }

    @Test
    public void mustTypeNewPass(){
        //this message will appear regardless if the current password is correct,
        //or if they've written something in the Confirm Password field

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        String currentPass = "patrick!";

        Assert.assertTrue("Error message did not appear", securityOverviewPage.noNewPassword(currentPass));

    }

    @Test
    public void mustTypeSecureNewPass(){
        //this message will appear if there is something written in Current, and something in New
        //you don't need to confirm your new pass to get this error message

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        String currentPass = "patrick!";
        String newPass = "123";

        Assert.assertTrue("Error message did not appear", securityOverviewPage.notSecurePass(currentPass, newPass));

    }

    @Test
    public void mustConfirmPass(){
        //for this message to display, something must be written in the Current field, and
        //there must be a New password that is secure
        //either nothing written in Confirm, or the wrong password

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        String currentPass = "patrick!";
        String newPass = "q4p@ss123";

        Assert.assertTrue("Error message did not appear", securityOverviewPage.noConfirmationPass(currentPass, newPass));

    }

    @Test
    public void oldPassMustBeCorrect(){
        //the old pass must be wrong, and both (new and confirm), but be secure and match
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        String currentPass = "BLAH!";
        String newPass = "q4p@ss123";

        Assert.assertTrue("Error message did not appear", securityOverviewPage.wrongOldPass(currentPass, newPass));

    }

    @Test
    public void newPassCannotMatchOld(){
        //Current must be filled, and correct
        //New must be the same as Current
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        String samePassword = "patrick!";

        Assert.assertTrue("Error message did not appear", securityOverviewPage.samePassAsBefore(samePassword));

    }

    @Test
    public void canCancelChangePassword(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        Assert.assertTrue("Cancellation was not successful", securityOverviewPage.cancelChangePassword());
    }

    //logout test cases

    @Test
    public void canLogOut(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();
        securityOverviewPage.logoutFromPage();

        Assert.assertEquals("Logout was unsuccessful", 2, driver.findElements(By.className("q4i-logo")).size());

    }

    @Test
    public void canCancelLogOut(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        Assert.assertTrue("Logout cancellation was not successful", securityOverviewPage.cancelLogoutFromPage());

    }

}
