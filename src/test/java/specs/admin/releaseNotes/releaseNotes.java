package specs.admin.releaseNotes;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.releaseNotesPage.CreateReleaseNotesPage;
import pageobjects.admin.releaseNotesPage.ReleaseNotesPage;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by dannyl on 2017-08-03.
 */
public class releaseNotes extends AdminAbstractSpec {

    @Before
    public void setUp(){
        new AdminLoginPage(driver).loginAdmin()
                .navigateToReleaseNotesPage();
    }

    @Test
    public void canSaveReleaseNote(){
        String version = "0.0.5";
        String title = "Big Baller Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);

        CreateReleaseNotesPage createReleaseNotesPage = new CreateReleaseNotesPage(driver);
        new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview);

        Assert.assertTrue("Publish Button doesn't exist", createReleaseNotesPage.checkCanSaveReleaseNote(title));

    }

    @Test
    public void canDeleteReleaseNote(){
        String version = "0.0.5";
        String title = "Big Baller Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);

       ReleaseNotesPage releaseNotesPage = new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview)
                .deleteReleaseNote(title);

        Assert.assertFalse("Release Note was not deleted properly", releaseNotesPage.checkCanDeleteReleaseNote(title).contains(title));

    }

    @Test
    public void canCancelDeleteReleaseNote(){
        String version = "0.0.5";
        String title = "Big Baller Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);
        ReleaseNotesPage releaseNotesPage = new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview)
                .cancelDeleteReleaseNote(title);

        Assert.assertThat("Cancelling deletion failed", releaseNotesPage.getTitleOnDetailsPage(), containsString("Edit Release Note"));
    }

    @Test
    public void canSearchForReleaseNote(){
        String version = "0.0.5";
        String title = "Big Baller Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);
        ReleaseNotesPage releaseNotesPage = new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview)
                .navigateToReleaseNotesPage()
                .searchForNote(title);

        Assert.assertThat("Can't find press release", releaseNotesPage.getTitle(), containsString(title));

    }

}
