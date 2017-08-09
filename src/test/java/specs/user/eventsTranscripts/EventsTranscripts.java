package specs.user.eventsTranscripts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.eventsTranscriptsPage.EventsTranscriptsPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by dannyl on 2017-08-08.
 */
public class EventsTranscripts extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectEventsAndTranscriptsFromSideNav();
    }

    @Test
    public void canFilterByDay (){
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by day properly", eventsTranscriptsPage.filterByDay());
    }

    @Test
    public void canFilterByMonth (){
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by month properly", eventsTranscriptsPage.filterByMonth());
    }

    // Improvements needed
    @Ignore
    public void canFilterByWeek (){
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by week properly", eventsTranscriptsPage.filterByWeek());
    }

    @Test
    public void canFilterByToday (){
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by today properly", eventsTranscriptsPage.filterByToday());
    }

    @Test
    public void canFilterByTranscript (){
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by transcript properly", eventsTranscriptsPage.filterByTranscript());
    }
}
