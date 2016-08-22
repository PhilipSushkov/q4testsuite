package specs.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.activityPage.ActivityPage;
import pageobjects.logActivity.LogActivityModal;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-22.
 */
public class LogActivityFromActivityPage extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser("patrickp@q4inc.com", "patrick!")
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    @Test
    public void canLogNoteFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()
                .enterNoteDetails(comment, note, tag);

        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
    }
}
