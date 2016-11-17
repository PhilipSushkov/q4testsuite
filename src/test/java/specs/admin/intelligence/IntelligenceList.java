package specs.admin.intelligence;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.intelligencePage.IntelligencePage;
import pageobjects.admin.intelligencePage.WTSReportDetailsPage;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by patrickp on 2016-11-09.
 */
public class IntelligenceList extends AdminAbstractSpec {

    @Before
    public void setUp() {
        new AdminLoginPage(driver).loginAdmin()
                .navigateToIntelligencePage();
    }

    @Test
    public void canCreateNewTradeSummary() throws ParseException {
        DateTime dateTime = new DateTime();
        Date date = dateTime.toDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, YYYY");
        String dateText = simpleDateFormat.format(date);

        String symbol = "SYY";
        String type = "Weekly Trade Summary";
        String reportTitle = "Sysco Corp. | SYY | XNYS\n" +
                "Weekly Trade Summary";

        IntelligencePage intelligencePage = new IntelligencePage(driver).createWeeklyTradeSummary(symbol);

        Assert.assertThat("Stock symbol does not match expected", intelligencePage.getNewReport(), containsString(symbol));
        Assert.assertThat("Report type does not match expected", intelligencePage.getNewReport(), containsString(type));
        Assert.assertThat("New report date does not match expected", intelligencePage.getNewReport(), containsString(dateText));

        intelligencePage.selectNewReport();

        Assert.assertThat("Report title does not match expected", intelligencePage.getReportHeader(), containsString(reportTitle));
    }

    @Test
    public void canCreateNewOptionsAnalytics() {
        DateTime dateTime = new DateTime().minusHours(4);
        Date date = dateTime.toDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, YYYY");
        String dateText = simpleDateFormat.format(date);

        String symbol = "SYY";
        String type = "Weekly Options Analytics";
        String reportTitle = "Sysco Corp. | SYY | XNYS\n" +
                "Weekly Options Analytics";

        IntelligencePage intelligencePage = new IntelligencePage(driver).createWeeklyOptionsAnalytics(symbol);

        Assert.assertThat("Stock symbol does not match expected", intelligencePage.getNewReport(), containsString(symbol));
        Assert.assertThat("Report type does not match expected", intelligencePage.getNewReport(), containsString(type));
        Assert.assertThat("New report date does not match expected", intelligencePage.getNewReport(), containsString(dateText));

        intelligencePage.selectNewReport();

        Assert.assertThat("Report title does not match expected", intelligencePage.getReportHeader(), containsString(reportTitle));
    }

    @Test
    public void canCreateNewSalesEquityAndOptions() {
        DateTime dateTime = new DateTime().minusHours(4);
        Date date = dateTime.toDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, YYYY");
        String dateText = simpleDateFormat.format(date);

        String symbol = "SYY";
        String type = "Sales Equity And Options";
        String reportTitle = "Sysco Corp. | SYY | XNYS\n" +
                "Sales Equity And Options";

        IntelligencePage intelligencePage = new IntelligencePage(driver).createSalesEquitAndOptions(symbol);

        Assert.assertThat("Stock symbol does not match expected", intelligencePage.getNewReport(), containsString(symbol));
        Assert.assertThat("Report type does not match expected", intelligencePage.getNewReport(), containsString(type));
        Assert.assertThat("New report date does not match expected", intelligencePage.getNewReport(), containsString(dateText));

        intelligencePage.selectNewReport();

        Assert.assertThat("Report title does not match expected", intelligencePage.getReportHeader(), containsString(reportTitle));
    }

    @Test
    public void canDeleteExistingReport() {
        String symbol = "SYY";
        IntelligencePage intelligencePage = new IntelligencePage(driver).createWeeklyTradeSummary(symbol);

        String newReportDateTime = intelligencePage.getNewReportDateTime();

        intelligencePage.selectNewReport()
                .deleteReport();

        Assert.assertThat("Deleted report is still visible in list", intelligencePage.getEntireReportList(), not(containsString(newReportDateTime)));
    }

    @Test
    public void canSeeOnlyPendingReports(){
        Assert.assertTrue("One or more displayed reports are not of status 'Pending'.", new IntelligencePage(driver).showPendingReports().allReportsHaveStatus("Pending"));
    }

    @Test
    public void canSeeOnlyReadyReports(){
        Assert.assertTrue("One or more displayed reports are not of status 'Ready'.", new IntelligencePage(driver).showReadyReports().allReportsHaveStatus("Ready"));
    }

    @Test
    public void canSeeOnlyApprovedReports(){
        Assert.assertTrue("One or more displayed reports are not of status 'Approved'.", new IntelligencePage(driver).showApprovedReports().allReportsHaveStatus("Approved"));
    }

    @Test
    public void canSeeOnlyReadyToPublishReports(){
        Assert.assertTrue("One or more displayed reports are not of status 'Ready To Publish'.", new IntelligencePage(driver).showReadyToPublishReports().allReportsHaveStatus("Ready To Publish"));
    }

    @Test
    public void canSeeOnlyWeeklyTradeSummaryReports(){
        Assert.assertTrue("One or more displayed reports are not of type 'Weekly Trade Summary'.",
                new IntelligencePage(driver).showReportsOfType("Weekly Trade Summary").allReportsAreOfType("Weekly Trade Summary"));
    }

    @Test
    public void canSeeOnlyWeeklyOptionsAnalyticsReports(){
        Assert.assertTrue("One or more displayed reports are not of type 'Weekly Options Analytics'.",
                new IntelligencePage(driver).showReportsOfType("Weekly Options Analytics").allReportsAreOfType("Weekly Options Analytics"));
    }

    @Test
    public void canSeeOnlySalesEquityAndOptionsReports(){
        Assert.assertTrue("One or more displayed reports are not of type 'Sales Equity And Options'.",
                new IntelligencePage(driver).showReportsOfType("Sales Equity And Options").allReportsAreOfType("Sales Equity And Options"));
    }

    @Test
    public void canApproveNewReports(){
        // creating new report
        String symbol = "IBM";
        String reportTitle = "International Business Machines Corp | IBM | XNYS\n" +
                "Weekly Trade Summary";
        IntelligencePage intelligencePage = new IntelligencePage(driver).createWeeklyTradeSummary(symbol);
        // checking that report is listed
        Assert.assertThat("Expected stock symbol doesn't match with first listed report", intelligencePage.getNewReport(), containsString(symbol));
        // opening report and check that page has been opened
        WTSReportDetailsPage wtsReportDetailsPage = intelligencePage.selectNewReport();
        Assert.assertThat("Report title does not match expected", wtsReportDetailsPage.getReportHeader(), containsString(reportTitle));
        // approving report and check that button says "Approved"
        Assert.assertTrue("Report is not marked as 'Approved'.", wtsReportDetailsPage.approveReport().reportIsApproved());
        // checking that report can be downloaded
        Assert.assertTrue("Download button is not present.", wtsReportDetailsPage.downloadButtonIsPresent());
    }

    @Test
    public void canProduceValidWeeklyTradeSummaryReport(){
        String[] symbols = {"FCEL", "WPRT", "BLDP", "HYGS", "AMSC", "FSLR", "CLNE", "PLUG"};
        String[] companies = {"FuelCell Energy, Inc.", "Westport Fuel Systems, Inc.",  "Ballard Power Systems, Inc.", "Hydrogenics Corp.",
                "American Superconductor Corp.", "First Solar, Inc.", "Clean Energy Fuels Corp.", "Plug Power, Inc."};
        String reportTitle = "FuelCell Energy, Inc. | FCEL | XNAS\n" +
                "Weekly Trade Summary";
        IntelligencePage intelligencePage = new IntelligencePage(driver).createWeeklyTradeSummary(symbols[0]);
        Assert.assertThat("Expected stock symbol doesn't match with first listed report", intelligencePage.getNewReport(), containsString(symbols[0]));
        WTSReportDetailsPage wtsReportDetailsPage = intelligencePage.selectNewReport();
        Assert.assertThat("Report title does not match expected", wtsReportDetailsPage.getReportHeader(), containsString(reportTitle));
        Assert.assertTrue("Report contains invalid data.", wtsReportDetailsPage.reportDataIsValid(symbols, companies));
    }
}
