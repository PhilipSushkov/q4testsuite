package pageobjects.admin.intelligencePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by patrickp on 2016-11-09.
 */
public class WTSReportDetailsPage extends AbstractPageObject {
    private final By reportHeader = By.cssSelector(".page-header .page-title .details");
    private final By closingPrice = By.cssSelector(".crowded > div:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2)");

    public WTSReportDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getReportHeader() {
        waitForLoadingScreen();
        return findElement(reportHeader).getText();
    }

    public float getClosePrice() {
        return Float.parseFloat(findElement(closingPrice).getText());
    }

    public float getLastClosePrice(String company) throws IOException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        Stock stock = YahooFinance.get(company, cal, cal, Interval.WEEKLY);
        String test = String.valueOf(stock.getHistory()).replaceAll("", "").trim();

        String done = test.substring(test.indexOf("(") + 1, test.indexOf(")"));

        return Float.parseFloat(done);
    }
}
