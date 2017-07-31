package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.sideNavBar.SideNavBar;
import specs.AbstractSpec;

/**
 * Created by zacharyk on 2017-07-17.
 */
public class BulkActivityActions extends AbstractSpec {
    public final String keyword = "**BULK DELETE**";

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    @Test
    public void createAndBulkDeleteNotes() {
        String comment, note, tag;

        ActivityPage activityPage = new ActivityPage(driver);

        try {

            for (int i=0; i<5; i++) {
            comment = keyword + "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
            note = "Test note" + RandomStringUtils.randomAlphanumeric(6);
            tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

            activityPage.logNote()
                    .enterNoteDetails(comment, note, tag);

            activityPage.accessSideNavFromPage().selectActivityPageFromSideNav();
            }

            activityPage.searchForNote(keyword);

            for (int i=0; i<3; i++) {
                activityPage.clickNthActivityCheckBox(i);
            }


            activityPage.clickDeleteButton();
            Thread.sleep(2000L);
            Assert.assertEquals(activityPage.getNumberOfDisplayedActivities(), 2);

            activityPage.deleteAllNotes(keyword);
            Thread.sleep(200L);
            Assert.assertEquals(activityPage.getNumberOfDisplayedActivities(), 0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
