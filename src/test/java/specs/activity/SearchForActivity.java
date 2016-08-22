package specs.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.activityPage.ActivityPage;
import pageobjects.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-22.
 */
public class SearchForActivity extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser("patrickp@q4inc.com", "patrick!")
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    @Test
    public void canSearchForActivity() {
        String comment1 = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note1 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag1 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        String comment2 = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note2 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag2 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.logNote()
                .enterNoteDetails(comment1, note1, tag1);

        activityPage.logNote()
                .enterNoteDetails(comment2, note2, tag2);

        activityPage.searchForNote(comment2);

        Assert.assertThat(activityPage.getSearchResults(), containsString(comment2));
    }

    @Test
    public void searchForActivityThatDoesNotExist() {
        String noResults = "No activities available.";
        ActivityPage activityPage = new ActivityPage(driver).searchForNote("123456789");

        Assert.assertThat(activityPage.getNoNote(), containsString(noResults));
    }
}
