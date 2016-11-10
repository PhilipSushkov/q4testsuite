package specs.admin.intelligence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.intelligencePage.IntelligencePage;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;

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
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, YYYY");
        String dateText = simpleDateFormat.format(date);

        String symbol = "SYY";
        String type = "Weekly Trade Summary";
        String reportTitle = "Sysco Corp. | SYY | XNYS\n" +
                "Weekly Trade Summary";

        IntelligencePage intelligencePage = new IntelligencePage(driver).createWeeklyTradeSummary(symbol);

        Assert.assertThat(intelligencePage.getNewReport(), containsString(symbol));
        Assert.assertThat(intelligencePage.getNewReport(), containsString(type));
        Assert.assertThat(intelligencePage.getNewReport(), containsString(dateText));

        intelligencePage.selectNewReport();

        Assert.assertThat(intelligencePage.getReportHeader(), containsString(reportTitle));
    }

    @Test
    public void canCreateNewOptionsAnalytics() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, YYYY");
        String dateText = simpleDateFormat.format(date);

        String symbol = "SYY";
        String type = "Weekly Options Analytics";
        String reportTitle = "Sysco Corp. | SYY | XNYS\n" +
                "Weekly Options Analytics";

        IntelligencePage intelligencePage = new IntelligencePage(driver).createWeeklyOptionsAnalytics(symbol);

        Assert.assertThat(intelligencePage.getNewReport(), containsString(symbol));
        Assert.assertThat(intelligencePage.getNewReport(), containsString(type));
        Assert.assertThat(intelligencePage.getNewReport(), containsString(dateText));

        intelligencePage.selectNewReport();

        Assert.assertThat(intelligencePage.getReportHeader(), containsString(reportTitle));
    }

    @Test
    public void canCreateNewSalesEquityAndOptions() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, YYYY");
        String dateText = simpleDateFormat.format(date);

        String symbol = "SYY";
        String type = "Sales Equity And Options";
        String reportTitle = "Sysco Corp. | SYY | XNYS\n" +
                "Sales Equity And Options";

        IntelligencePage intelligencePage = new IntelligencePage(driver).createSalesEquitAndOptions(symbol);

        Assert.assertThat(intelligencePage.getNewReport(), containsString(symbol));
        Assert.assertThat(intelligencePage.getNewReport(), containsString(type));
        Assert.assertThat(intelligencePage.getNewReport(), containsString(dateText));

        intelligencePage.selectNewReport();

        Assert.assertThat(intelligencePage.getReportHeader(), containsString(reportTitle));
    }
}
