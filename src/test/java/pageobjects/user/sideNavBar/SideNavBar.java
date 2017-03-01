package pageobjects.user.sideNavBar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.advancedSearchResultsPage.AdvancedSearchResults;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.eventsTranscriptsPage.EventsTranscriptsPage;
import pageobjects.user.myQ4TeamPage.MyQ4TeamPage;
import pageobjects.user.briefingBooks.BriefingBookList;
import pageobjects.user.researchPage.ResearchPage;
import pageobjects.user.estimatesPage.SecurityEstimatesPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import pageobjects.user.ownershipPage.SecurityOwnershipPage;
import pageobjects.user.supportTicketsPage.SupportTicketsPage;
import pageobjects.user.targeting.TargetingPage;
import pageobjects.user.watchlist.WatchlistPage;
import pageobjects.user.webAnalyticsPage.WebAnalyticsPage;
import pageobjects.user.webcastAnalyticsPage.WebcastAnalyticsPage;

/**
 * Created by patrickp on 2016-08-04.
 */
public class SideNavBar extends AbstractPageObject{

    private final By dashBoardSideNav = By.cssSelector(".x-treelist-q4-desktop-navigation .x-treelist-item-text");
    private final By watchlistSideNav = By.id("ext-treelistitem-2");
    private final By activityPageSideNav = By.id("ext-treelistitem-3");
    private final By contactsSideNav = By.id("ext-treelistitem-4");
    private final By eventsSideNav = By.id("ext-treelistitem-5");
    private final By reportsSideNav = By.id("ext-treelistitem-6");
    private final By targetingSideNav = By.id("ext-treelistitem-7");
    private final By advancedSearchSideNav = By.id("ext-treelistitem-9");
    private final By securitySideNav = By.id("ext-treelistitem-10");
    private final By ownershipSideNav = By.id("ext-treelistitem-11");
    private final By estimatesSideNav = By.id("ext-treelistitem-12");
    private final By researchSideNav = By.id("ext-treelistitem-13");
    private final By webAnalyticsSideNav = By.id("ext-treelistitem-14");
    private final By webcastAnalyticsSideNav = By.id("ext-treelistitem-15");
    private final By myQ4TeamSideNav = By.id("ext-treelistitem-17");
    private final By supportTicketsSideNav = By.id("ext-treelistitem-18");
    private final By checklistSideNav = By.id("ext-treelistitem-19");



    public SideNavBar(WebDriver driver) {
        super(driver);
    }


    public Dashboard selectDashboardFromSideNav() {
        pause(2000L);
        waitForElementToAppear(dashBoardSideNav);
        findElement(dashBoardSideNav).click();

        return new Dashboard(getDriver());
    }

    public WatchlistPage selectWatchListFromSideNav() {
        pause(2000L);
        waitForElementToAppear(watchlistSideNav);
        findElement(watchlistSideNav).click();

        return new WatchlistPage(getDriver());
    }

    public ActivityPage selectActivityPageFromSideNav() {
        pause(2000L);
        waitForElementToAppear(activityPageSideNav);
        findElement(activityPageSideNav).click();

        return new ActivityPage(getDriver());
    }

    public ContactPage selectContactsFromSideNav() {
        pause(2000L);
        wait.until(ExpectedConditions.elementToBeClickable(contactsSideNav));
        findElement(contactsSideNav).click();

        return new ContactPage(getDriver());
    }

    public EventsTranscriptsPage selectEventsAndTranscriptsFromSideNav() {
        pause(2000L);
        waitForElementToAppear(eventsSideNav);
        findElement(eventsSideNav).click();

        return new EventsTranscriptsPage(getDriver());
    }

    public BriefingBookList selectBriefingBookFromSideNav() {
        pause(2000L);
        waitForElementToAppear(reportsSideNav);
        findElement(reportsSideNav).click();

        return new BriefingBookList(getDriver());
    }

    public SecurityOwnershipPage selectOwnershipFromSideNav() {
        pause(2000L);
        waitForElementToAppear(ownershipSideNav);
        findElement(ownershipSideNav).click();
        waitForLoadingScreen();

        return new SecurityOwnershipPage(getDriver());
    }

    public SecurityEstimatesPage selectEstimatesFromSideNav() {
        pause(2000L);
        waitForElementToAppear(estimatesSideNav);
        findElement(estimatesSideNav).click();

        return new SecurityEstimatesPage(getDriver());
    }

    public WebAnalyticsPage selectWebAnalyticsFromSideNav() {
        pause(2000L);
        waitForElementToAppear(webAnalyticsSideNav);
        findElement(webAnalyticsSideNav).click();

        return new WebAnalyticsPage(getDriver());
    }

    public WebcastAnalyticsPage selectWebcastAnalyticsFromSideNav() {
        pause(2000L);
        waitForElementToAppear(webcastAnalyticsSideNav);
        findElement(webcastAnalyticsSideNav).click();

        return new WebcastAnalyticsPage(getDriver());
    }

    public MyQ4TeamPage selectMyQ4TeamFromSideNav() {
        pause(2000L);
        waitForElementToAppear(myQ4TeamSideNav);
        findElement(myQ4TeamSideNav).click();

        return new MyQ4TeamPage(getDriver());
    }

    public SupportTicketsPage selectSupportTicketsFromSideNav() {
        pause(2000L);
        waitForElementToAppear(supportTicketsSideNav);
        findElement(supportTicketsSideNav).click();

        return new SupportTicketsPage(getDriver());
    }

    public SecurityOverviewPage selectSecurityFromSideNav() {
        pause(2000L);
        waitForElementToAppear(securitySideNav);
        findElement(securitySideNav).click();

        return new SecurityOverviewPage(getDriver());
    }

    public TargetingPage selectTargetingFromSideNav() {
        pause(2000L);
        waitForElement(targetingSideNav);
        findElement(targetingSideNav).click();

        return new TargetingPage(getDriver());
    }

    public AdvancedSearchResults selectAdvancedSearchFromSideNav() {
        pause(2000L);
        waitForElement(advancedSearchSideNav);
        findElement(advancedSearchSideNav).click();

        return new AdvancedSearchResults(getDriver());
    }

    public ResearchPage selectResearchFromSideNav() {
        pause(2000L);
        wait.until(ExpectedConditions.elementToBeClickable(researchSideNav));
        findElement(researchSideNav).click();

        return new ResearchPage(getDriver());
    }
}
