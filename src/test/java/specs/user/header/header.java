package specs.user.header;

import org.junit.*;
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
    public void canViewReleaseNotesFromSecurity(){


        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        securityOwnershipPage.openProfile();
        securityOwnershipPage.clickReleaseNotes();

        String actualURL = driver.getCurrentUrl();

        Assert.assertEquals("This is not the release notes page", releaseNotes, actualURL);

    }

    @Test
    public void canLeaveFeedbackFromSecurity(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.openProfile();
        securityOverviewPage.leaveBlankFeedback();



    }

}
