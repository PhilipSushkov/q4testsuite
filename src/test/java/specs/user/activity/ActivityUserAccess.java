package specs.user.activity;

import org.junit.*;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import java.util.List;

/**
 * Created by charleszheng on 10/30/2017.
 */

public class ActivityUserAccess extends AbstractSpec  {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    @Test
    public void checkActivityUserAccess(){
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.searchForNote("binbin");
        List<String> userOne = activityPage.getActivityTitles();
        activityPage.logOut();
        new LoginPage(driver).customLoginUser("juntianz@q4inc.com","asdf1234!")
                .accessSideNav()
                .selectActivityPageFromSideNav();
        activityPage.searchForNote("binbin");
        List<String> userTwo = activityPage.getActivityTitles();
        Assert.assertTrue("Activities should be different for different users", activityPage
                .showsDifferentActivitiesForDifferentUser(userOne, userTwo));

    }
}
