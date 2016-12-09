package specs.user.activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
        ActivityPage activityPage = new ActivityPage(driver).clickFilterCheckbox(email);
        Assert.assertTrue("Filter not applied correctly for emails",activityPage.isFilteredCorrectly(email));
    }

    @Test
    public void filterActivitiesByNote(){
        FilterType note = FilterType.NOTE;
        ActivityPage activityPage = new ActivityPage(driver).clickFilterCheckbox(note);
        Assert.assertTrue("Filter not applied correctly for notes",activityPage.isFilteredCorrectly(note));
    }

    @Test
    public void filterActivitiesByMeeting(){
        FilterType meeting = FilterType.MEETING;
        ActivityPage activityPage = new ActivityPage(driver).clickFilterCheckbox(meeting) ;
        Assert.assertTrue("Filter not applied correctly for meetingss",activityPage.isFilteredCorrectly(meeting));
    }

    @Test
    public void filterActivitiesByPhone(){
        FilterType phone = FilterType.PHONE;
        ActivityPage activityPage = new ActivityPage(driver).clickFilterCheckbox(phone);
        Assert.assertTrue("Filter not applied correctly for phone",activityPage.isFilteredCorrectly(phone));
    }

    @Test
    public void sortActivitiesByType(){
        ColumnType type = ColumnType.TYPE;
        ActivityPage activityPage = new ActivityPage(driver).clickColumnHeader(type);
        Assert.assertTrue("Type not sorted correctly",activityPage.isColumnSorted(type));
    }

    @Test
    public void sortActivitiesByTitle(){
        ColumnType title = ColumnType.TITLE;
        ActivityPage activityPage = new ActivityPage(driver).clickColumnHeader(title);
        Assert.assertTrue("Type not sorted correctly",activityPage.isColumnSorted(title));
    }

    @Test
    public void sortActivitiesByDate(){
        ColumnType date = ColumnType.DATE;
        ActivityPage activityPage = new ActivityPage(driver).clickColumnHeader(date);
        Assert.assertTrue("Type not sorted correctly",activityPage.isColumnSorted(date));
    }
}
