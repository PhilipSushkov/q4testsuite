package specs.user.estimates;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.Calendar;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.estimatesPage.SecurityEstimatesPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by kelvint on 11/2/16.
 */
public class Estimates extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectEstimatesFromSideNav();
    }

    @Test
    public void canViewConsensusTabs() {
        Assert.assertTrue("Consensus tabs do not function properly", new SecurityEstimatesPage(driver).checkConsensusTabs());
    }

    @Test
    public void canViewBrokerDetailTabs() {
        Assert.assertTrue("Broker Detail tabs to not function properly", new SecurityEstimatesPage(driver).checkBrokerDetailTabs());
    }

    @Test
    public void canChangeBrokerDetailDate() {
        SecurityEstimatesPage securityEstimatesPage = new SecurityEstimatesPage(driver);

        String currentFirstBrokerEntry = securityEstimatesPage.getNthRowText(0);

        for (int i=0; i<5; i++) {
            securityEstimatesPage.changeBrokerDetailsDate(i);
            Assert.assertNotEquals("First table entry didn't change when new date was selected",
                    currentFirstBrokerEntry, securityEstimatesPage.getNthRowText(0));
        }
    }

    @Test
    public void canSelectAnalystFromTable() {
        SecurityEstimatesPage securityEstimatesPage = new SecurityEstimatesPage(driver);

        String broker = securityEstimatesPage.getNthBroker(0);
        String analyst = securityEstimatesPage.getNthAnalyst(0);

        ContactDetailsPage analystPage = securityEstimatesPage.selectNthAnalyst(0);

        Assert.assertTrue("Did not open correct contact page", analystPage.getContactName().contains(analyst));
        Assert.assertTrue("Did not open correct contact page", analystPage.getInstitutionName()
                // brokers sometimes don't match because of bracketed detail
                .contains(broker.replaceAll("\\(\\w+\\)", "")));
    }

    @Test
    public void historicalTableExists() {
        Assert.assertTrue("Historical data table does not appear", new SecurityEstimatesPage(driver).checkHistoricalTable());
    }

    @Test
    public void historicalTableValuesAreValid() {
        Assert.assertTrue("Historical data table has invalid value", new SecurityEstimatesPage(driver).checkHistoricalTableValues());
    }

    //Test for the research section at the bottom of the page
    @Test
    public void canSortResearchByDate() {
        // Checking all research items fall within a designated time frame selected through "start" and "end" times
        SecurityEstimatesPage estimatesPage = new SecurityEstimatesPage(driver);
        Calendar calendar = new Calendar(driver);
        estimatesPage.filterDate(calendar);

        Assert.assertTrue("Research Reports cannot be sorted by date.", new SecurityEstimatesPage(driver).sortByDateRange(calendar));
    }

    @Test
    public void canSortResearchByHeadline() {
        SecurityEstimatesPage estimatesPage = new SecurityEstimatesPage(driver);
        Calendar calendar = new Calendar(driver);
        estimatesPage.filterDate(calendar);

        Assert.assertTrue("Research Reports cannot be sorted by headline" , estimatesPage.sortByHeadline());
    }

    @Test
    public void canSortResearchByContributor() {
        SecurityEstimatesPage estimatesPage = new SecurityEstimatesPage(driver);
        Calendar calendar = new Calendar(driver);
        estimatesPage.filterDate(calendar);

        Assert.assertTrue("Research Reports cannot be sorted by contributor", estimatesPage.sortByContributor());
    }

    @Test
    @Ignore
    public void canSortResearchByAnalyst() {
        SecurityEstimatesPage estimatesPage = new SecurityEstimatesPage(driver);
        Calendar calendar = new Calendar(driver);
        estimatesPage.filterDate(calendar);

        Assert.assertTrue("KNOWN ISSUE: DESKTOP 9124 - Research Reports cannot be sorted by analyst", estimatesPage.sortByAnalyst());
    }

    @Test
    public void canSearchForResearch() {
        SecurityEstimatesPage estimatesPage = new SecurityEstimatesPage(driver);
        String lastHeadline = estimatesPage.getLastResearchHeadline().getText();

        Assert.assertTrue("Search did not return any results", estimatesPage.getNumberOfResearch()>0);

        String searchedResult = estimatesPage.searchForResearch(lastHeadline.replaceAll("[.]", ""))
                // search did not recognize a specific title because of included periods
                .getNthResearchHeadline(0)
                .getText();

        Assert.assertTrue("Search found the wrong result", searchedResult.equals(lastHeadline));

    }

    @Test
    public void canDownloadResearchPdf() {
        SecurityEstimatesPage estimatesPage = new SecurityEstimatesPage(driver);
        String report = estimatesPage.getNthResearchHeadline(0).getText();
        estimatesPage.getNthResearchHeadline(0).click();
        estimatesPage.waitForLoadingScreen();

        Assert.assertFalse("Downloaded report was empty", estimatesPage.getReportPdfContent(report).isEmpty());
    }

    @Test
    public void canShowMoreResearch() {
        SecurityEstimatesPage estimatesPage = new SecurityEstimatesPage(driver);

        int reportsNum = estimatesPage.getNumberOfResearch();
        estimatesPage.showMoreResearch();
        Assert.assertTrue("Clicking 'Show More' did not show more research", estimatesPage.getNumberOfResearch() > reportsNum);
    }
}
