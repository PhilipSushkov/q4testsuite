package specs.admin.intelligence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.intelligencePage.WTSReportDetailsPage;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;

import java.io.IOException;

/**
 * Created by patrickp on 2016-11-10.
 */
public class WTSReportDetails extends AdminAbstractSpec {

    private static boolean setUpIsDone = false;
    private static String company = "SYY";

    @Before
    public void setUp() {
        if (setUpIsDone) {
        new AdminLoginPage(driver).loginAdmin()
                .navigateToIntelligencePage()
                .selectNewReport();
            return;
        }

        new AdminLoginPage(driver).loginAdmin()
                .navigateToIntelligencePage()
                .createWeeklyTradeSummary(company)
                .selectNewReport();

        setUpIsDone = true;
    }

    @Test
    public void verifyClosePrice() throws IOException {
        WTSReportDetailsPage wtsReportDetailsPage = new WTSReportDetailsPage(driver);
        Float closePrice = wtsReportDetailsPage.getComparisonClosePrice(company);

        Assert.assertEquals("Difference between prices is greater that $0.5", closePrice, wtsReportDetailsPage.getClosePrice(), 0.5);
    }
}


