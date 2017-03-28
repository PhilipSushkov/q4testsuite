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
    public void filterActivitiesByRoadshow(){
        FilterType roadshow = FilterType.ROADHSHOW;
        ActivityPage activityPage = new ActivityPage(driver).clickFilterCheckbox(roadshow) ;
        Assert.assertTrue("Filter not applied correctly for meetingss",activityPage.isFilteredCorrectly(roadshow));
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
        Assert.assertTrue("Title not sorted correctly",activityPage.isColumnSorted(title));
    }

    @Test
    public void sortActivitiesByDate(){
        ColumnType date = ColumnType.DATE;
        ActivityPage activityPage = new ActivityPage(driver).clickColumnHeader(date);
        Assert.assertTrue("Date not sorted correctly",activityPage.isColumnSorted(date));
    }

    @Test
    public void multiSort(){
        //This checks that the sorting works even when it's been clicked before

        ColumnType type = ColumnType.TYPE;
        ColumnType title = ColumnType.TITLE;
        ColumnType date = ColumnType.DATE;

        boolean actualSort[] = new boolean[6];
        boolean expectedSort[] = {true, true, true, true, true, true};

        ActivityPage activityPage = new ActivityPage(driver).clickColumnHeader(date);
        actualSort[0] = activityPage.isColumnSorted(date);

        activityPage.clickColumnHeader(type);
        actualSort[1] = activityPage.isColumnSorted(type);

        activityPage.clickColumnHeader(title);
        actualSort[2] = activityPage.isColumnSorted(title);

        //now going back, and clicking those filters again (to see if clicking them a second time has any effect
        activityPage.clickColumnHeader(date);
        actualSort[3] = activityPage.isColumnSorted(date);

        activityPage.clickColumnHeader(title);
        actualSort[4] = activityPage.isColumnSorted(title);

        activityPage.clickColumnHeader(type);
        actualSort[5] = activityPage.isColumnSorted(type);

        Assert.assertArrayEquals("Sorting failed", actualSort, expectedSort);


    }
}
