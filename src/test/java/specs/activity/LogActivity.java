package specs.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.activityPage.ActivityPage;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by patrickp on 2016-08-22.
 */
public class LogActivity extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    @Test
    public void canLogNoteFromActivityPage() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        int count = new ActivityPage(driver).getNoteCount();

        System.out.println(new ActivityPage(driver).getNoteCount());

        ActivityPage activityPage = new ActivityPage(driver);
        new ActivityPage(driver).logNote()
                .enterNoteDetails(comment, note, tag);

        // Make sure the new comment appears on page
        Assert.assertThat(activityPage.getNewNote(), containsString(comment));
        // Make sure the note count increased by 1
        Assert.assertThat(activityPage.getNoteCount(), (equalTo((count + 1 ))));
    }
}
