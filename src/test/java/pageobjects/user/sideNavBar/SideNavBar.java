package pageobjects.user.sideNavBar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.advancedSearchPage.AdvancedSearchPage;
import pageobjects.user.briefingBooks.BriefingBookList;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.estimatesPage.SecurityEstimatesPage;
import pageobjects.user.eventsTranscriptsPage.EventsTranscriptsPage;
import pageobjects.user.ownershipPage.SecurityOwnershipPage;
import pageobjects.user.researchPage.ResearchPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
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
    private final By advancedSearchSideNav = By.id("ext-treelistitem-8");
    private final By securitySideNav = By.id("ext-treelistitem-10");
    private final By ownershipSideNav = By.id("ext-treelistitem-11");
    private final By estimatesSideNav = By.id("ext-treelistitem-13");
    private final By researchSideNav = By.id("ext-treelistitem-14");
    private final By webAnalyticsSideNav = By.id("ext-treelistitem-15");
    private final By webcastAnalyticsSideNav = By.id("ext-treelistitem-16");
    private final By supportTicketsSideNav = By.id("ext-treelistitem-17");
    private final By checklistSideNav = By.id("ext-treelistitem-19");



    public SideNavBar(WebDriver driver) {
        super(driver);
    }


    public Dashboard selectDashboardFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(dashBoardSideNav);
        findElement(dashBoardSideNav).click();

        return new Dashboard(getDriver());
    }

    public WatchlistPage selectWatchListFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(watchlistSideNav);
        findElement(watchlistSideNav).click();

        return new WatchlistPage(getDriver());
    }

    public ActivityPage selectActivityPageFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(activityPageSideNav);
        new Actions(driver).moveToElement(findElement(activityPageSideNav)).click().perform();
        waitForLoadingScreen();

        return new ActivityPage(getDriver());
    }

    public ContactPage selectContactsFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(contactsSideNav);
        findElement(contactsSideNav).click();

        return new ContactPage(getDriver());
    }

    public EventsTranscriptsPage selectEventsAndTranscriptsFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(eventsSideNav);
        findElement(eventsSideNav).click();

        return new EventsTranscriptsPage(getDriver());
    }

    public BriefingBookList selectBriefingBookFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(reportsSideNav);
        findElement(reportsSideNav).click();

        return new BriefingBookList(getDriver());
    }

    public SecurityOwnershipPage selectOwnershipFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(ownershipSideNav);
        findElement(ownershipSideNav).click();
        waitForLoadingScreen();

        return new SecurityOwnershipPage(getDriver());
    }

    public SecurityEstimatesPage selectEstimatesFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(estimatesSideNav);
        findElement(estimatesSideNav).click();

        return new SecurityEstimatesPage(getDriver());
    }

    public WebAnalyticsPage selectWebAnalyticsFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(webAnalyticsSideNav);
        findElement(webAnalyticsSideNav).click();

        return new WebAnalyticsPage(getDriver());
    }

    public WebcastAnalyticsPage selectWebcastAnalyticsFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(webcastAnalyticsSideNav);
        findElement(webcastAnalyticsSideNav).click();

        return new WebcastAnalyticsPage(getDriver());
    }

    public SupportTicketsPage selectSupportTicketsFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(supportTicketsSideNav);
        findElement(supportTicketsSideNav).click();

        return new SupportTicketsPage(getDriver());
    }

    public SecurityOverviewPage selectSecurityFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(securitySideNav);
        findElement(securitySideNav).click();

        return new SecurityOverviewPage(getDriver());
    }

    public TargetingPage selectTargetingFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(targetingSideNav);
        findElement(targetingSideNav).click();

        return new TargetingPage(getDriver());
    }

    public AdvancedSearchPage selectAdvancedSearchFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(advancedSearchSideNav);
        findElement(advancedSearchSideNav).click();

        return new AdvancedSearchPage(getDriver());
    }

    public ResearchPage selectResearchFromSideNav() {
        pause(2000L);
        waitForElementToBeClickable(researchSideNav);
        findElement(researchSideNav).click();

        return new ResearchPage(getDriver());
    }
}
