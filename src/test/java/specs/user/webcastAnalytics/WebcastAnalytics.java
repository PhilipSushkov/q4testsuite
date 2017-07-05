package specs.user.webcastAnalytics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.webcastAnalyticsPage.WebcastAnalyticsDetailsPage;
import specs.AbstractSpec;

/**
 * Created by andyp on 2017-05-10.
 */
public class WebcastAnalytics extends AbstractSpec {

    @Before
    public void setup(){
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectWebcastAnalyticsFromSideNav();
    }

    @Test
    public void checkWebcastTitle(){
        WebcastAnalyticsDetailsPage page = new WebcastAnalyticsDetailsPage(driver);
        String title = page.getWebcastTitle();
        page.selectFirstWebcast();

        String detailsTitle = page.getDetailsWebcastTitle();

        Assert.assertEquals("Title is not the equal", title, detailsTitle);

    }

    @Test
    public void checkWebcastRegistrants(){
        WebcastAnalyticsDetailsPage page = new WebcastAnalyticsDetailsPage(driver);
        String registered = page.getWebcastRegistrants();
        page.selectFirstWebcast();

        String detailsRegistered = page.getDetailsRegisteredAttendees();
        Assert.assertEquals("Number of registrants are not equal", registered, detailsRegistered);

    }

    @Test
    public void checkWebcastOnDemand(){
        WebcastAnalyticsDetailsPage page = new WebcastAnalyticsDetailsPage(driver);
        String onDemand = page.getWebcastOnDemandAttendees();
        page.selectFirstWebcast();

        String detailsOnDemand = page.getDetailsOnDemandAttendees();
        Assert.assertEquals("Number of ondemand attendees are not equal", onDemand, detailsOnDemand);

    }

    @Test
    public void checkWebcastTotal(){
        WebcastAnalyticsDetailsPage page = new WebcastAnalyticsDetailsPage(driver);
        String total = page.getWebcastTotalAttendees();
        page.selectFirstWebcast();

        String detailsTotal = page.getDetailsTotalAttendees();
        Assert.assertEquals("Number of total attendees are not eqaul", total, detailsTotal);

    }
}
