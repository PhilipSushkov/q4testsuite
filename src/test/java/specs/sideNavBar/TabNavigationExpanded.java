package specs.sideNavBar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.activityPage.ActivityPage;
import pageobjects.checklistPage.ChecklistPage;
import pageobjects.contactPage.ContactPage;
import pageobjects.dashboardPage.Dashboard;
import pageobjects.eventsTranscriptsPage.EventsTranscriptsPage;
import pageobjects.loginPage.LoginPage;
import pageobjects.myQ4TeamPage.MyQ4TeamPage;
import pageobjects.reportBuilder.ReportBuilderPage;
import pageobjects.securityPage.SecurityEstimatesPage;
import pageobjects.securityPage.SecurityOverviewPage;
import pageobjects.securityPage.SecurityOwnershipPage;
import pageobjects.supportTicketsPage.SupportTicketsPage;
import pageobjects.watchlist.WatchlistPage;
import pageobjects.webAnalyticsPage.WebAnalyticsPage;
import pageobjects.webcastAnalyticsPage.WebcastAnalyticsPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-04.
 */
public class TabNavigationExpanded extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
    }


    @Test
     public void canNavigateToDashboard() {
        String searchFieldText = "What are you looking for?";
        Dashboard start = new Dashboard(driver);
        start.accessSideNav()
                .selectDashboardFromSideNav();

        Assert.assertEquals(searchFieldText, start.getSearchFieldText());
    }


    @Test
    public void canNavigateToWatchlist() {
        String pageTitle = "Watchlist";
        WatchlistPage finish = new WatchlistPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectWatchListFromSideNav();

        Assert.assertThat(finish.getWatchlistPageTitle(), containsString(pageTitle));
    }


    @Test
    public void canNavigateToActivity() {
        String pageTitle = "Activity";
        ActivityPage finish = new ActivityPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectActivityPageFromSideNav();

        Assert.assertEquals(pageTitle, finish.getActivityPageTitle());
        System.out.println(finish.getActivityPageTitle());
    }

    @Test
    public void canNavigateToContacts() {
        String pageTitle = "Contacts";
        ContactPage contactDetailsPage = new ContactPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectContactsFromSideNav();

        Assert.assertThat(contactDetailsPage.getContactsPageTitle(), containsString(pageTitle));
    }

    @Test
    public void canNavigateToEventsAndTranscripts() {
        String pageTitle = "Events & Transcripts";
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectEventsAndTranscriptsFromSideNav();

        Assert.assertThat(eventsTranscriptsPage.getEventsPageTitle(), containsString(pageTitle));
    }

    @Test
    public void canNavigateToReportBuilder() {
        String pageTitle = "Reports";
        ReportBuilderPage reportBuilderPage = new ReportBuilderPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectReportBuilderFromSideNav();

        Assert.assertThat(reportBuilderPage.getReportBuilderPageTitle(), containsString(pageTitle));
    }

    @Test
    public void canNavigateToSecurity() {
        String pageTitle = "LinkedIn Corp.";
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectSecurityFromSideNav();

        Assert.assertThat(securityOverviewPage.getCompanyName(), containsString(pageTitle));
    }

    @Test
    public void canNavigateToOwnership() {
        String tabTitle = "OWNERSHIP";
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectOwnershipFromSideNav();

        Assert.assertThat(securityOwnershipPage.getOwnershipTabTitle(), containsString(tabTitle));
    }

    @Test
    public void canNavigateToEsitmates() {
        String tabTitle = "ESTIMATES";
        SecurityEstimatesPage securityEstimatesPage = new SecurityEstimatesPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectEstimatesFromSideNav();

        Assert.assertThat(securityEstimatesPage.getEstimatesTabTitle(), containsString(tabTitle));
    }

    @Ignore
    @Test
    public void canNavigateToWebAnalytics() {
        String pageTitle = "Web Analytics";
        WebAnalyticsPage webAnalyticsPage = new WebAnalyticsPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectWebAnalyticsFromSideNav();

        Assert.assertThat(webAnalyticsPage.getWebAnalyticsPageTitle(), containsString(pageTitle));
    }

    @Test
    public void canNavigateToWebcastAnalytics() {
        String pageTitle = "Webcast Analytics";
        WebcastAnalyticsPage webcastAnalyticsPage = new WebcastAnalyticsPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectWebcastAnalyticsFromSideNav();

        Assert.assertThat(webcastAnalyticsPage.getWecastAnalyticsPageTitle(), containsString(pageTitle));
    }

    @Test
    public void canNavigateToMyQ4Team() {
        String pageTitle = "Q4 Team";
        MyQ4TeamPage myQ4TeamPage = new MyQ4TeamPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectMyQ4TeamFromSideNav();

        Assert.assertThat(myQ4TeamPage.getMyQ4TeamPageTitle(), containsString(pageTitle));
    }

    @Test
    public void canNavigateToSupportTickets() {
        String pageTitle = "Support Tickets";
        SupportTicketsPage supportTicketsPage = new SupportTicketsPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectSupportTicketsFromSideNav();

        Assert.assertThat(supportTicketsPage.getSupportTicketsPageTitle(), containsString(pageTitle));
    }

    @Test
    public void canNavigateToChecklist() {
        String pageTitle = "Checklist";
        ChecklistPage checklistPage = new ChecklistPage(driver);
        new Dashboard(driver).accessSideNav()
                .selectChecklistFromSideNav();

        Assert.assertThat(checklistPage.getChecklistPageTitle(), containsString(pageTitle));
    }
}
