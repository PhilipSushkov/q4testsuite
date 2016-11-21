package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.Wait;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.activityPage.ColumnType;
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
    public void filterActivitiesByNote(){
        FilterType note = FilterType.NOTE;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickFiltersButton().clickFilterCheckbox(note).clickSearch();
        Assert.assertTrue("Filter not applied correctly for notes",activityPage.isFilteredCorrectly(note));
    }


    @Test
    public void filterActivitiesByMeeting(){
        FilterType meeting = FilterType.MEETING;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickFiltersButton().clickFilterCheckbox(meeting).clickSearch();
        Assert.assertTrue("Filter not applied correctly for meetingss",activityPage.isFilteredCorrectly(meeting));
    }

    @Test
    public void filterActivitiesByPhone(){
        FilterType phone = FilterType.PHONE;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickFiltersButton().clickFilterCheckbox(phone).clickSearch();
        Assert.assertTrue("Filter not applied correctly for phone",activityPage.isFilteredCorrectly(phone));
    }

    @Test
    public void sortActivitiesByType(){
        ColumnType type = ColumnType.TYPE;
        ActivityPage activityPage = new ActivityPage(driver);
       activityPage.clickColumnHeader(type);
        Assert.assertTrue("Type not sorted correctly",activityPage.isColumnSorted(type));
    }

    @Test
    public void sortActivitiesByTitle(){
        ColumnType title = ColumnType.TITLE;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickColumnHeader(title);
        Assert.assertTrue("Type not sorted correctly",activityPage.isColumnSorted(title));
    }

    @Test
    public void sortActivitiesByDate(){
        ColumnType date = ColumnType.DATE;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickColumnHeader(date);
        Assert.assertTrue("Type not sorted correctly",activityPage.isColumnSorted(date));
    }

}
