package specs.admin.intelligence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.intelligencePage.WTSReportDetailsPage;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

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

        Stock stock = YahooFinance.get(company);
        float price = stock.getQuote().getPreviousClose().floatValue();
        System.out.println(price);

        Assert.assertEquals(price, wtsReportDetailsPage.getClosePrice(), 0.5);
    }
}


