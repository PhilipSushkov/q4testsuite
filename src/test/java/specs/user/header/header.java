package specs.user.header;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Pause;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.fundPage.FundPage;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;

import java.util.regex.Pattern;

/**
 * Created by sarahr on 2/9/2017.
 */
public class header extends AbstractSpec{

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectSecurityFromSideNav();
    }

    //in chat
    @Test
    public void canClearMessage(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openChat();

        String message = "This is  QA test!";

        Assert.assertTrue("The message did not clear", securityOverviewPage.clearChat(message));

    }

    @Test
    public void canSearchForSecurityFromHeader() {
        String securitySearchTerm = "Facebook";
        String expectedSecurity = "Facebook, Inc.";

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.searchResults(securitySearchTerm, expectedSecurity)
            .waitForLoadingScreen();

        Assert.assertEquals("Did not open correct security page", securityOverviewPage.getCompanyName(), "Facebook Inc");
    }

    @Test
    public void canSearchForInstitutionFromHeader() {
        String institutionSearchTerm = "vanguard";
        String expectedInstitution = "The Vanguard Group, Inc";

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.searchResults(institutionSearchTerm, expectedInstitution)
                .waitForLoadingScreen();

        InstitutionPage institutionPage = new InstitutionPage(driver);

        Assert.assertTrue("Did not open correct institution page", institutionPage.getInstitutionName().contains(expectedInstitution));
    }

    @Test
    public void canSearchForContactFromHeader() {
        String contactSearchTerm = "andrew mccormick";
        String expectedContact = "Andrew C. McCormick";

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.searchResults(contactSearchTerm, expectedContact)
                .waitForLoadingScreen();

        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);

        Assert.assertEquals("Did not open correct contact page", contactDetailsPage.getContactName(), "Mr. Andrew C. McCormick");
    }

    @Test
    public void canSearchForFundFromHeader() {
        String fundSearchTerm = "belfund";
        String expectedFund = "Belfund";

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.searchResults(fundSearchTerm, expectedFund)
                .waitForLoadingScreen();

        FundPage fundPage = new FundPage(driver);

        Assert.assertTrue("Did not open correct fund page", fundPage.getFundName().contains(expectedFund));
    }


    @Test
    public void canViewReleaseNotes(){
        String releaseNotes = driver.getCurrentUrl().replaceAll("\\bcompany.*\\b", "release-note");
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();
        securityOverviewPage.clickReleaseNotes();
        securityOverviewPage.waitForLoadingScreen();
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

        Assert.assertEquals("Logout was unsuccessful", 1, driver.findElements(By.className("q4i-logo")).size());

    }
}
