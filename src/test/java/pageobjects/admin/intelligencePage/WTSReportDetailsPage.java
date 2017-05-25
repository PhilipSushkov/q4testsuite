package pageobjects.admin.intelligencePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;
import pageobjects.api.historical.QuandlConnectToApi;
import pageobjects.api.historical.QuandlDataset;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * Created by patrickp on 2016-11-09.
 */
public class WTSReportDetailsPage extends AbstractPageObject {
    private final By reportHeader = By.cssSelector(".page-header .page-title .details");
    private final By closingPrice = By.cssSelector(".crowded > div:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2)");
    private final By deleteButton = By.cssSelector(".remove");
    private final By approveButton = By.className("button-dark-grey");
    private final By downloadButton = By.className("download");

    // stock trade summary table
    private final By weekClose = By.cssSelector("q4-table-trade-summary td:nth-child(2)");
    private final By weekChange = By.cssSelector("q4-table-trade-summary td:nth-child(3)");
    private final By weekChangeP = By.cssSelector("q4-table-trade-summary td:nth-child(4)");
    private final By weekHigh = By.cssSelector("q4-table-trade-summary td:nth-child(5)");
    private final By weekLow = By.cssSelector("q4-table-trade-summary td:nth-child(6)");
    private final By weekVolume = By.cssSelector("q4-table-trade-summary td:nth-child(7)");
    private final By averageWeekVolume = By.cssSelector("q4-table-trade-summary td:nth-child(8)");
    private final By volumeVariance = By.cssSelector("q4-table-trade-summary td:nth-child(9)");
    private final By qtdChange = By.cssSelector("q4-table-trade-summary td:nth-child(10)");
    private final By ytdChange = By.cssSelector("q4-table-trade-summary td:nth-child(11)");

    // indices summary table
    private final By indicesClose = By.cssSelector("q4-table-indices td:nth-child(2)");
    private final By indicesChange = By.cssSelector("q4-table-indices td:nth-child(3)");
    private final By indicesChangeP = By.cssSelector("q4-table-indices td:nth-child(4)");
    private final By indicesQTDChange = By.cssSelector("q4-table-indices td:nth-child(5)");
    private final By indicesYTDChange = By.cssSelector("q4-table-indices td:nth-child(6)");

    // Q4 projected volatility table

    // daily performance chart
    private final By dailyPerformanceHoverText = By.cssSelector("q4-chart-daily-changes .highcharts-tooltip text");
    private final By dailyPerformanceStockBars = By.cssSelector("q4-chart-daily-changes .highcharts-series-0 rect");
    private final By dailyPerformancePeerBars = By.cssSelector("q4-chart-daily-changes .highcharts-series-1 rect");
    private final By dailyPerformanceSP500Bars = By.cssSelector("q4-chart-daily-changes .highcharts-series-2 rect");

    // YTD change chart
    private final By ytdChangeBars = By.cssSelector("q4-chart-ytd-change .highcharts-point");
    private final By ytdChangeAxisText = By.cssSelector("q4-chart-ytd-change .highcharts-xaxis-labels tspan");
    private final By ytdChangeHoverText = By.cssSelector("q4-chart-ytd-change .highcharts-tooltip text");

    Actions actions = new Actions(driver);

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

    public float getComparisonClosePrice(String company) throws IOException {
        // Method to get today's date
        Calendar cal = Calendar.getInstance();
        // Go back one day because there is not stock data for today yet
        cal.add(Calendar.DATE, -1);
        Date today = cal.getTime();
        // Format the date so QuandlAPI can read it
        DateFormat todaysDate = new SimpleDateFormat("yyyy-MM-dd");
        String inputDate = todaysDate.format(today);

        //Creates a QuandlDataset that contains the close price from a specific date
        QuandlDataset stock = QuandlConnectToApi.getDatasetFromDate(company, "EOD", inputDate);
        String lastClosePrice = stock.getClosingPrices().get(0);

        return Float.parseFloat(lastClosePrice);
    }

    public IntelligencePage deleteReport() {
        waitForLoadingScreen();
        findElement(deleteButton).click();

        return new IntelligencePage(getDriver());
    }

    public WTSReportDetailsPage approveReport(){
        waitForLoadingScreen();
        findElement(approveButton).click();
        return this;
    }

    public boolean reportIsApproved(){
        waitForLoadingScreen();
        return findElement(approveButton).getText().equalsIgnoreCase("Approved");
    }

    public boolean downloadButtonIsPresent(){
        return doesElementExist(downloadButton) && findElement(downloadButton).isDisplayed();
    }

    /*
    This method verifies:
    - all “Stock Trade Summary” data
    - “Indices” data for Dow Jones, Nasdaq, S&P 500, and 10 Year Yield
    - values displayed when hovering over the bars of the “Daily Performance” chart
    - values displayed when hovering over the bars of the “YTD Change” chart
    Explanations on how values are supposed to be calculated: https://q4websystems.atlassian.net/wiki/display/PM/Intelligence+Reports
    First value in parameter String[] symbols should be the symbol of the company, followed by the symbols of each peer in the order presented on the report.
     */
    public boolean reportDataIsValid(String[] symbols){
        boolean allValid = true;
        ZonedDateTime lastFriday = ZonedDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)); //most-recent date that report should be based off of (may be incorrect if last friday is not a trading day)
        ZonedDateTime thirteenWeeksEarlier = lastFriday.minusDays(95);
        ZonedDateTime endOfLastQuarter;
        ZonedDateTime endOfLastYear = lastFriday.minusYears(1).with(TemporalAdjusters.lastDayOfYear());
        if (lastFriday.getMonthValue() > 3){
            endOfLastQuarter = lastFriday.withMonth((lastFriday.getMonthValue()-1)/3*3).with(TemporalAdjusters.lastDayOfMonth());
        }
        else {
            endOfLastQuarter = endOfLastYear;
        }

        // determining and calculating "Stock Trade Summary" values (using data from Yahoo)
        List<List<HistoricalQuote>> companyStocksLast13Weeks = new ArrayList<>(); //first index is for each symbol, second index is for each day of data gathered (starting from most recent)
        List<List<HistoricalQuote>> companyStocksEndOfLastQuarter = new ArrayList<>();
        List<List<HistoricalQuote>> companyStocksEndOfLastYear = new ArrayList<>();
        double[] expectedClose = new double[symbols.length];
        double[] previousClose = new double[symbols.length];
        double[] expectedWeekChange = new double[symbols.length];
        double[] expectedWeekChangeP = new double[symbols.length];
        double expectedWeekChangePAverage = 0;
        double[] expectedHigh = new double[symbols.length];
        double[] expectedLow = new double[symbols.length];
        long[] expectedVolume = new long[symbols.length];
        long[] expectedVolumeWeekAverage = new long[symbols.length];
        double[] expectedVolumeVariance = new double[symbols.length];
        double[] lastQuarterClose = new double[symbols.length];
        double[] lastYearClose = new double[symbols.length];
        double[] expectedQuarterToDateChange = new double[symbols.length];
        double expectedQuarterToDateChangeAverage = 0;
        double[] expectedYearToDateChange = new double[symbols.length];
        double expectedYearToDateChangeAverage = 0;
        for (int stock=0; stock<symbols.length; stock++){
            expectedHigh[stock] = 0;
            expectedLow[stock] = Double.POSITIVE_INFINITY;
            expectedVolume[stock] = 0;
            expectedVolumeWeekAverage[stock] = 0;
        }
        //fetching stock data from Yahoo
        try {
            for (int stock=0; stock<symbols.length; stock++){
                companyStocksLast13Weeks.add(YahooFinance.get(symbols[stock]).getHistory(GregorianCalendar.from(thirteenWeeksEarlier), GregorianCalendar.from(lastFriday), Interval.DAILY));
                companyStocksEndOfLastQuarter.add(YahooFinance.get(symbols[stock]).getHistory(GregorianCalendar.from(endOfLastQuarter.minusDays(7)), GregorianCalendar.from(endOfLastQuarter), Interval.DAILY));
                companyStocksEndOfLastYear.add(YahooFinance.get(symbols[stock]).getHistory(GregorianCalendar.from(endOfLastYear.minusDays(7)), GregorianCalendar.from(endOfLastYear), Interval.DAILY));
            }
        }catch (IOException e){
            System.out.println("Unable to retrieve historical stock quotes from Yahoo Finance.");
            return false;
        }
        //calculating values for each stock
        for (int stock=0; stock<symbols.length; stock++){
            // calculating high price, low price, and volume for the week
            for (int day=0; day<5; day++){
                if (companyStocksLast13Weeks.get(stock).get(day).getHigh().doubleValue() > expectedHigh[stock]){
                    expectedHigh[stock] = companyStocksLast13Weeks.get(stock).get(day).getHigh().doubleValue();
                }
                if (companyStocksLast13Weeks.get(stock).get(day).getLow().doubleValue() < expectedLow[stock]){
                    expectedLow[stock] = companyStocksLast13Weeks.get(stock).get(day).getLow().doubleValue();
                }
                expectedVolume[stock] += companyStocksLast13Weeks.get(stock).get(day).getVolume();
            }
            expectedClose[stock] = companyStocksLast13Weeks.get(stock).get(0).getClose().doubleValue(); //last friday's closing price
            previousClose[stock] = companyStocksLast13Weeks.get(stock).get(5).getClose().doubleValue(); //friday before's closing price
            expectedWeekChange[stock] = expectedClose[stock] - previousClose[stock];
            expectedWeekChangeP[stock] = 100 * expectedWeekChange[stock] / previousClose[stock];
            // calculating average weekly volume over the last 13 weeks
            for (int day=5; day<companyStocksLast13Weeks.get(stock).size(); day++){
                expectedVolumeWeekAverage[stock] += companyStocksLast13Weeks.get(stock).get(day).getVolume();
            }
            expectedVolumeWeekAverage[stock] /= 13;
            expectedVolumeVariance[stock] = 100 * (expectedVolume[stock] - expectedVolumeWeekAverage[stock]) / expectedVolumeWeekAverage[stock];
            // determining end-of-quarter and end-of-year prices
            lastQuarterClose[stock] = companyStocksEndOfLastQuarter.get(stock).get(0).getClose().doubleValue();
            lastYearClose[stock] = companyStocksEndOfLastYear.get(stock).get(0).getClose().doubleValue();
            expectedQuarterToDateChange[stock] = 100 * (expectedClose[stock] - lastQuarterClose[stock]) / lastQuarterClose[stock];
            expectedYearToDateChange[stock] = 100 * (expectedClose[stock] - lastYearClose[stock]) / lastYearClose[stock];
        }
        // calculating peer average values
        for (int stock=1; stock<symbols.length; stock++){
            expectedWeekChangePAverage += expectedWeekChangeP[stock];
            expectedQuarterToDateChangeAverage += expectedQuarterToDateChange[stock];
            expectedYearToDateChangeAverage += expectedYearToDateChange[stock];
        }
        expectedWeekChangePAverage /= symbols.length-1;
        expectedQuarterToDateChangeAverage /= symbols.length-1;
        expectedYearToDateChangeAverage /= symbols.length-1;


        // determining and calculating "Indices" values (using data from Yahoo)
        String[] indices = {"^DJI", "^IXIC", "^GSPC", "^TNX"/*, "GC=F", "CL=F"*/};//could not find symbols in Yahoo for gold and oil that could provide historical data
        List<List<HistoricalQuote>> indicesLastWeek = new ArrayList<>();//first index is for each index symbol, second index is for each day of data gathered (starting from most recent)
        List<List<HistoricalQuote>> indicesEndOfLastQuarter = new ArrayList<>();
        List<List<HistoricalQuote>> indicesEndOfLastYear = new ArrayList<>();
        double[] indicesExpectedClose = new double[indices.length];
        double[] indicesPreviousClose = new double[indices.length];
        double[] indicesExpectedWeekChange = new double[indices.length];
        double[] indicesExpectedWeekChangeP = new double[indices.length];
        double[] indicesLastQuarterClose = new double[indices.length];
        double[] indicesLastYearClose = new double[indices.length];
        double[] indicesExpectedQuarterToDateChange = new double[indices.length];
        double[] indicesExpectedYearToDateChange = new double[indices.length];
        //fetching index data from Yahoo
        try {
            for (int index=0; index<indices.length; index++){
                indicesLastWeek.add(YahooFinance.get(indices[index]).getHistory(GregorianCalendar.from(lastFriday.minusDays(10/*7*/)), GregorianCalendar.from(lastFriday), Interval.DAILY));
                indicesEndOfLastQuarter.add(YahooFinance.get(indices[index]).getHistory(GregorianCalendar.from(endOfLastQuarter.minusDays(7)), GregorianCalendar.from(endOfLastQuarter), Interval.DAILY));
                indicesEndOfLastYear.add(YahooFinance.get(indices[index]).getHistory(GregorianCalendar.from(endOfLastYear.minusDays(7)), GregorianCalendar.from(endOfLastYear), Interval.DAILY));
            }
        }catch (IOException e){
            System.out.println("Unable to retrieve indices quotes from Yahoo Finance.");
            return false;
        }
        //calculating values for each index
        for (int index=0; index<indices.length; index++){
            indicesExpectedClose[index] = indicesLastWeek.get(index).get(0).getClose().doubleValue();//last friday's closing price
            indicesPreviousClose[index] = indicesLastWeek.get(index).get(5).getClose().doubleValue();//friday before's closing price
            indicesExpectedWeekChange[index] = indicesExpectedClose[index] - indicesPreviousClose[index];
            indicesExpectedWeekChangeP[index] = 100 * indicesExpectedWeekChange[index] / indicesPreviousClose[index];
            indicesLastQuarterClose[index] = indicesEndOfLastQuarter.get(index).get(0).getClose().doubleValue();
            indicesLastYearClose[index] = indicesEndOfLastYear.get(index).get(0).getClose().doubleValue();
            indicesExpectedQuarterToDateChange[index] = 100 * (indicesExpectedClose[index] - indicesLastQuarterClose[index]) / indicesLastQuarterClose[index];
            indicesExpectedYearToDateChange[index] = 100 * (indicesExpectedClose[index] - indicesLastYearClose[index]) / indicesLastYearClose[index];
        }

        // determining and calculating "Daily Performance" values (using data from Yahoo)
        double[] stockDailyPerformance = new double[5];
        double[] peerDailyPerformance = new double[5];
        double[] sp500DailyPerformance = new double[5];
        List<HistoricalQuote> sp500LastWeek;
        //fetching S&P 500 data from Yahoo
        try {
            sp500LastWeek = YahooFinance.get("^GSPC").getHistory(GregorianCalendar.from(lastFriday.minusDays(10/*7*/)), GregorianCalendar.from(lastFriday), Interval.DAILY);
        }catch (IOException e){
            System.out.println("Unable to retrieve S&P 500 quotes from Yahoo Finance.");
            return false;
        }
        // calculating daily performance for each day
        for (int day=0, chartDay=4; day<5; day++, chartDay--){ //stock data starts from most recent day, chart goes from Monday to Friday
            double stockClose = companyStocksLast13Weeks.get(0).get(day).getClose().doubleValue();
            double previousStockClose = companyStocksLast13Weeks.get(0).get(day+1).getClose().doubleValue();//closing price of day before
            stockDailyPerformance[chartDay] = 100 * (stockClose - previousStockClose) / previousStockClose;
            //calculating peer average
            peerDailyPerformance[chartDay] = 0;
            for (int stock=1; stock<symbols.length; stock++){
                double peerClose = companyStocksLast13Weeks.get(stock).get(day).getClose().doubleValue();
                double previousPeerClose = companyStocksLast13Weeks.get(stock).get(day+1).getClose().doubleValue();
                peerDailyPerformance[chartDay] += 100 * (peerClose - previousPeerClose) / previousPeerClose;
            }
            peerDailyPerformance[chartDay] /= symbols.length-1;
            double sp500Close = sp500LastWeek.get(day).getClose().doubleValue();
            double previousSP500Close = sp500LastWeek.get(day+1).getClose().doubleValue();
            sp500DailyPerformance[chartDay] = 100 * (sp500Close - previousSP500Close) / previousSP500Close;
        }


        // verifying that "Stock Trade Summary" values are correct
        for (int stock=0; stock<symbols.length; stock++){
            if (Math.abs(Double.parseDouble(findElements(weekClose).get(stock).getText()) - expectedClose[stock]) > 0.02){ // checks if calculated value is within 0.02 of displayed
                System.out.println(symbols[stock]+"'s week close is inaccurate.\n\tExpected: "+expectedClose[stock]+"\n\tDisplayed: "+findElements(weekClose).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekChange).get(stock).getText()) - expectedWeekChange[stock]) > 0.02){
                System.out.println(symbols[stock]+"'s week change is inaccurate.\n\tExpected: "+expectedWeekChange[stock]+"\n\tDisplayed: "+findElements(weekChange).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekChangeP).get(stock).getText()) - expectedWeekChangeP[stock]) > 0.02){
                System.out.println(symbols[stock]+"'s week change % is inaccurate.\n\tExpected: "+expectedWeekChangeP[stock]+"\n\tDisplayed: "+findElements(weekChangeP).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekHigh).get(stock).getText()) - expectedHigh[stock]) > 0.02){
                System.out.println(symbols[stock]+"'s week high price is inaccurate.\n\tExpected: "+expectedHigh[stock]+"\n\tDisplayed: "+findElements(weekHigh).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekLow).get(stock).getText()) - expectedLow[stock]) > 0.02){
                System.out.println(symbols[stock]+"'s week low price is inaccurate.\n\tExpected: "+expectedLow[stock]+"\n\tDisplayed: "+findElements(weekLow).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekVolume).get(stock).getText())*10E5 - expectedVolume[stock]) > 20000){
                System.out.println("Known issue - ADMIN-399 - "+symbols[stock]+"'s week volume is inaccurate.\n\tExpected: "+expectedVolume[stock]+"\n\tDisplayed: "+findElements(weekVolume).get(stock).getText()+" million");
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(averageWeekVolume).get(stock).getText())*10E5 - expectedVolumeWeekAverage[stock]) > 20000){
                System.out.println(symbols[stock]+"'s average weekly volume is inaccurate.\n\tExpected: "+expectedVolumeWeekAverage[stock]+"\n\tDisplayed: "+findElements(averageWeekVolume).get(stock).getText()+" million");
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(volumeVariance).get(stock).getText()) - expectedVolumeVariance[stock]) > 0.02){
                // when displayed value is wrong, determine if calculation using displayed values gives same result (meaning that only those other displayed values are wrong)
                if (100*(Double.parseDouble(findElements(weekVolume).get(stock).getText()) - Double.parseDouble(findElements(averageWeekVolume).get(stock).getText()))
                        /Double.parseDouble(findElements(averageWeekVolume).get(stock).getText()) - Double.parseDouble(findElements(volumeVariance).get(stock).getText()) < 0.02){
                    System.out.println(symbols[stock]+"'s volume variance is inaccurate due to carryover from inaccurate week volume or/and average weekly volume.");
                    allValid = false;
                }
                else {
                    System.out.println(symbols[stock]+"'s volume variance is inaccurate.\n\tExpected: "+expectedVolumeVariance[stock]+"\n\tDisplayed: "+findElements(volumeVariance).get(stock).getText());
                    allValid = false;
                }
            }
            if (Math.abs(Double.parseDouble(findElements(qtdChange).get(stock).getText()) - expectedQuarterToDateChange[stock]) > 0.02){
                System.out.println(symbols[stock]+"'s QTD change is inaccurate.\n\tExpected: "+expectedQuarterToDateChange[stock]+"\n\tDisplayed: "+findElements(qtdChange).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(ytdChange).get(stock).getText()) - expectedYearToDateChange[stock]) > 0.02){
                System.out.println(symbols[stock]+"'s YTD change is inaccurate.\n\tExpected: "+expectedYearToDateChange[stock]+"\n\tDisplayed: "+findElements(ytdChange).get(stock).getText());
                allValid = false;
            }
        }
        if (Math.abs(Double.parseDouble(findElements(weekChangeP).get(symbols.length).getText()) - expectedWeekChangePAverage) > 0.02){
            System.out.println("Peer average week change % is inaccurate.\n\tExpected: "+expectedWeekChangePAverage+"\n\tDisplayed: "+findElements(weekChangeP).get(symbols.length).getText());
            allValid = false;
        }
        if (Math.abs(Double.parseDouble(findElements(qtdChange).get(symbols.length).getText()) - expectedQuarterToDateChangeAverage) > 0.02){
            System.out.println("Peer average QTD change is inaccurate.\n\tExpected: "+expectedQuarterToDateChangeAverage+"\n\tDisplayed: "+findElements(qtdChange).get(symbols.length).getText());
            allValid = false;
        }
        if (Math.abs(Double.parseDouble(findElements(ytdChange).get(symbols.length).getText()) - expectedYearToDateChangeAverage) > 0.02){
            System.out.println("Peer average YTD change is inaccurate.\n\tExpected: "+expectedYearToDateChangeAverage+"\n\tDisplayed: "+findElements(ytdChange).get(symbols.length).getText());
            allValid = false;
        }


        // verifying that "Indices" values are correct
        for (int index = 0; index<indices.length; index++){
            if ( getNumFromText(findElements(indicesClose).get(index).getText()) - indicesExpectedClose[index]> 5){
                System.out.println("Index "+indices[index]+"'s week close is inaccurate.\n\tExpected: "+indicesExpectedClose[index]+"\n\tDisplayed: "+findElements(indicesClose).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesChange).get(index).getText()) - indicesExpectedWeekChange[index] > 5){
                System.out.println("Index "+indices[index]+"'s week change is inaccurate.\n\tExpected: "+indicesExpectedWeekChange[index]+"\n\tDisplayed: "+findElements(indicesChange).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesChangeP).get(index).getText()) - indicesExpectedWeekChangeP[index] > 0.25){
                System.out.println("Index "+indices[index]+"'s week change % is inaccurate.\n\tExpected: "+indicesExpectedWeekChangeP[index]+"\n\tDisplayed: "+findElements(indicesChangeP).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesQTDChange).get(index).getText()) - indicesExpectedQuarterToDateChange[index] > 0.25){
                System.out.println("Index "+indices[index]+"'s QTD change is inaccurate.\n\tExpected: "+indicesExpectedQuarterToDateChange[index]+"\n\tDisplayed: "+findElements(indicesQTDChange).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesYTDChange).get(index).getText()) - indicesExpectedYearToDateChange[index] > 0.25){
                System.out.println("Index "+indices[index]+"'s YTD change is inaccurate.\n\tExpected: "+indicesExpectedYearToDateChange[index]+"\n\tDisplayed: "+findElements(indicesYTDChange).get(index).getText());
                allValid = false;
            }
        }


        // verifying that "Daily Performance" values are correct (by hovering over each of the bars)
        int numDays= findVisibleElements(dailyPerformanceStockBars).size();
        for (int chartDay=0; chartDay<numDays; chartDay++){
            // checking for stock
            actions.clickAndHold(findVisibleElements(dailyPerformanceStockBars).get(chartDay)).perform();// clickAndHold needed to keep cursor there while getText() runs
            String hoverText = findElement(dailyPerformanceHoverText).getText().trim();
            if (!hoverText.startsWith(symbols[0])){
                System.out.println("Daily Performance: Hover text for blue bar on day "+(chartDay+1)+" does not start with desired symbol "+symbols[0]+"\n\tDisplayed hover text: "+hoverText);
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':')+2, hoverText.indexOf('%'))) - stockDailyPerformance[chartDay]) > 0.002){
                System.out.println("Stock's daily performance for day "+(chartDay+1)+" is inaccurate.\n\tExpected: "+stockDailyPerformance[chartDay]+"%\n\tDisplayed hover text: "+hoverText);
                allValid = false;
            }
            // checking for peer average
            actions.clickAndHold(findVisibleElements(dailyPerformancePeerBars).get(chartDay)).perform();
            hoverText = findElement(dailyPerformanceHoverText).getText().trim();
            if (!hoverText.startsWith("Peer Avg:")){
                System.out.println("Daily Performance: Hover text for yellow bar on day "+(chartDay+1)+" does not start with 'Peer Avg:'\n\tDisplayed hover text: "+hoverText);
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':')+2, hoverText.indexOf('%'))) - peerDailyPerformance[chartDay]) > 0.002){
                System.out.println("Peer average daily performance for day "+(chartDay+1)+" is inaccurate.\n\tExpected: "+peerDailyPerformance[chartDay]+"%\n\tDisplayed hover text: "+hoverText);
                allValid = false;
            }
            // checking for S&P 500
            actions.clickAndHold(findVisibleElements(dailyPerformanceSP500Bars).get(chartDay)).perform();
            hoverText = findElement(dailyPerformanceHoverText).getText().trim();
            if (!hoverText.startsWith("S&P 500:")){
                System.out.println("Daily Performance: Hover text for red bar on day "+(chartDay+1)+" does not start with 'S&P 500:'\n\tDisplayed hover text: "+hoverText);
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':')+2, hoverText.indexOf('%'))) - sp500DailyPerformance[chartDay]) > 0.002){
                System.out.println("S&P 500 daily performance for day "+(chartDay+1)+" is inaccurate.\n\tExpected: "+sp500DailyPerformance[chartDay]+"%\n\tDisplayed hover text: "+hoverText);
                allValid = false;
            }
        }

        ArrayList<WebElement> testArray = new ArrayList<>(findVisibleElements(ytdChangeBars));
        // verifying that "YTD Change" values are correct
        for (int bar=0; bar<symbols.length+1; bar++){

            actions.clickAndHold(findElements(ytdChangeBars).get(bar)).perform();// clickAndHold needed to keep cursor there while getText() runs
            String hoverText = findElement(ytdChangeHoverText).getText().trim();
            // checking the peer average bar
            if (findElement(ytdChangeAxisText).getText().contains("Peer")){
                if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':')+2, hoverText.indexOf('%'))) - expectedYearToDateChangeAverage) > 0.2){
                    allValid = false;
                    // when displayed value is wrong, determine if value is different from that displayed on Stock Trade Summary table (meaning that the graph is wrong)
                    if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':')+2, hoverText.indexOf('%')))
                            - Double.parseDouble(findElements(ytdChange).get(symbols.length).getText())) > 0.2){
                        System.out.println("YTD Change graph: Peer average bar is inaccurate.\n\tExpected: "+expectedYearToDateChangeAverage
                                +"%\n\tValue on STS table: "+findElements(ytdChange).get(symbols.length).getText()+"\n\tDisplayed hover text: "+hoverText);
                    }
                    else {
                        System.out.println("YTD Change graph: Peer average bar is inaccurate due to carryover from inaccurate value (see Stock Trade Summary table).");
                    }
                }
            }
            // checking the other bars
            else {
                for (int stock=0; stock<symbols.length; stock++){
                    if (findElements(ytdChangeAxisText).get(bar).getText().contains(symbols[stock])){ //matches each bar with its stock
                        if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':')+2, hoverText.indexOf('%'))) - expectedYearToDateChange[stock]) > 0.2){
                            allValid = false;
                            // when displayed value is wrong, determine if value is different from that displayed on Stock Trade Summary table (meaning that the graph is wrong)
                            if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':')+2, hoverText.indexOf('%')))
                                    - Double.parseDouble(findElements(ytdChange).get(stock).getText())) > 0.2){
                                System.out.println("YTD Change graph: "+symbols[stock]+" bar is inaccurate.\n\tExpected: "+expectedYearToDateChange[stock]
                                        +"%\n\tValue on STS table: "+findElements(ytdChange).get(stock).getText()+"\n\tDisplayed hover text: "+hoverText);
                            }
                            else {
                                System.out.println("YTD Change graph: "+symbols[stock]+" bar is inaccurate due to carryover from inaccurate value (see Stock Trade Summary table).");
                            }
                        }
                        break;
                    }
                }
            }
        }

        return allValid; // will be false if there were any inaccurate values (this way a full list of errors can be generated instead of stopping after one)
    }
}
