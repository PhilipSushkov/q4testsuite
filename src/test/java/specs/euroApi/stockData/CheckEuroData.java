package specs.euroApi.stockData;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Created by philipsushkov on 2017-04-03.
 */

public class CheckEuroData {

    @BeforeTest
    public void setUp() throws IOException {

    }

    @Test
    public void checkEuroData() throws IOException {
        HistoricalQuote lastTradingDayQuotes;

        lastTradingDayQuotes = YahooFinance.get("WDH.CO").getHistory().get(0);
        System.out.println("Yahoo Finance: " + lastTradingDayQuotes.getClose());
        //Assert.assertTrue(auth.getAccessToken(), "Access Token didn't receive");
    }

    @DataProvider
    public Object[][] getData() {
        return null;
    }

    @AfterTest
    public void tearDown() {

    }
}
