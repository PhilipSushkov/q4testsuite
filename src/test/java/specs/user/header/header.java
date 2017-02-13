package specs.user.header;

import com.applitools.shaded.eyessdk.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.*;
import org.openqa.selenium.By;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import pageobjects.user.securityPage.SecurityOwnershipPage;
import specs.AbstractSpec;
import specs.user.securityDetails.Ownership;

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

        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        securityOwnershipPage.openProfile();
        securityOwnershipPage.clickReleaseNotes();

        String actualURL = driver.getCurrentUrl();

        Assert.assertEquals("This is not the release notes page", releaseNotes, actualURL);

    }

    @Test
    public void canLeaveFeedback(){
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

    //Changing password test cases

    @Test
    public void mustTypeCurrentPass(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        String newPass = "q4p@ss123";

        Assert.assertTrue("Error message did not appear", securityOverviewPage.noCurrentPassword(newPass));
    }

    @Test
    public void mustTypeNewPass(){

    }

    @Test
    public void mustConfirmPass(){

    }

    @Test
    public void mustTypeSecureNewPass(){

    }

    @Test
    public void newPassCannotMatchOld(){

    }

    @Test
    public void newPassMustMatchConfirmPass(){

    }

    @Test
    public void oldPassMustBeCorrect(){

    }

    @Test
    public void canCancelChangePassword(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();

        Assert.assertTrue("Cancellation was not successful", securityOverviewPage.cancelChangePassword());
    }
}
