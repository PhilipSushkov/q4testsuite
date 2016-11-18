package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.Wait;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.activityPage.FilterType;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;


/**
 * Created by noelc on 2016-11-18.
 */
public class FilterActivity extends AbstractSpec {
    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }


    @Test
    public void filterActivitiesByEmail(){
        FilterType email = FilterType.EMAIL;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickFiltersButton().clickFilterCheckbox(email).clickSearch();
        Assert.assertTrue("Filter not applied correctly for emails",activityPage.isFilteredCorrectly(email));
    }


    @Test
    public void filterActiviesByNote(){
        FilterType note = FilterType.NOTE;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickFiltersButton().clickFilterCheckbox(note).clickSearch();
        Assert.assertTrue("Filter not applied correctly for notes",activityPage.isFilteredCorrectly(note));
    }


    @Test
    public void filterActiviesByMeeting(){
        FilterType meeting = FilterType.MEETING;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickFiltersButton().clickFilterCheckbox(meeting).clickSearch();
        Assert.assertTrue("Filter not applied correctly for meetingss",activityPage.isFilteredCorrectly(meeting));
    }

    @Test
    public void filterActiviesByPhone(){
        FilterType phone = FilterType.PHONE;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickFiltersButton().clickFilterCheckbox(phone).clickSearch();
        Assert.assertTrue("Filter not applied correctly for phone",activityPage.isFilteredCorrectly(phone));
    }
}
