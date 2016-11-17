package pageobjects.admin.intelligencePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
        String rawData = String.valueOf(stock.getHistory()).replaceAll("", "").trim();

        // After parsing the Yahoo csv file, the number within the parentheses is Friday's closing price
        String lastClosePrice = rawData.substring(rawData.indexOf("(") + 1, rawData.indexOf(")"));

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

    public boolean reportDataIsValid(String[] symbols, String[] companies){
        boolean allValid = true;
        ZonedDateTime lastFriday = ZonedDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
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
        List<List<HistoricalQuote>> companyStocksLast13Weeks = new ArrayList<>();
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
        for (int stock=0; stock<symbols.length; stock++){
            for (int day=0; day<5; day++){
                if (companyStocksLast13Weeks.get(stock).get(day).getHigh().doubleValue() > expectedHigh[stock]){
                    expectedHigh[stock] = companyStocksLast13Weeks.get(stock).get(day).getHigh().doubleValue();
                }
                if (companyStocksLast13Weeks.get(stock).get(day).getLow().doubleValue() < expectedLow[stock]){
                    expectedLow[stock] = companyStocksLast13Weeks.get(stock).get(day).getLow().doubleValue();
                }
                expectedVolume[stock] += companyStocksLast13Weeks.get(stock).get(day).getVolume();
            }
            expectedClose[stock] = companyStocksLast13Weeks.get(stock).get(0).getClose().doubleValue();
            previousClose[stock] = companyStocksLast13Weeks.get(stock).get(5).getClose().doubleValue();
            expectedWeekChange[stock] = expectedClose[stock] - previousClose[stock];
            expectedWeekChangeP[stock] = 100 * expectedWeekChange[stock] / previousClose[stock];
            for (int day=5; day<companyStocksLast13Weeks.get(stock).size(); day++){
                expectedVolumeWeekAverage[stock] += companyStocksLast13Weeks.get(stock).get(day).getVolume();
            }
            expectedVolumeWeekAverage[stock] /= 13;
            expectedVolumeVariance[stock] = 100 * (expectedVolume[stock] - expectedVolumeWeekAverage[stock]) / expectedVolumeWeekAverage[stock];
            lastQuarterClose[stock] = companyStocksEndOfLastQuarter.get(stock).get(0).getClose().doubleValue();
            lastYearClose[stock] = companyStocksEndOfLastYear.get(stock).get(0).getClose().doubleValue();
            expectedQuarterToDateChange[stock] = 100 * (expectedClose[stock] - lastQuarterClose[stock]) / lastQuarterClose[stock];
            expectedYearToDateChange[stock] = 100 * (expectedClose[stock] - lastYearClose[stock]) / lastYearClose[stock];
        }
        for (int stock=1; stock<symbols.length; stock++){
            expectedWeekChangePAverage += expectedWeekChangeP[stock];
            expectedQuarterToDateChangeAverage += expectedQuarterToDateChange[stock];
            expectedYearToDateChangeAverage += expectedYearToDateChange[stock];
        }
        expectedWeekChangePAverage /= symbols.length-1;
        expectedQuarterToDateChangeAverage /= symbols.length-1;
        expectedYearToDateChangeAverage /= symbols.length-1;


        // determining and calculating "Indices" values (using data from Yahoo)
        String[] indices = {"^DJI", "^IXIC", "^GSPC", "^TNX"/*, "GC=F", "CL=F"*/};
        List<List<HistoricalQuote>> indicesLastWeek = new ArrayList<>();
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
        for (int index=0; index<indices.length; index++){
            indicesExpectedClose[index] = indicesLastWeek.get(index).get(0).getClose().doubleValue();
            indicesPreviousClose[index] = indicesLastWeek.get(index).get(5).getClose().doubleValue();
            indicesExpectedWeekChange[index] = indicesExpectedClose[index] - indicesPreviousClose[index];
            indicesExpectedWeekChangeP[index] = 100 * indicesExpectedWeekChange[index] / indicesPreviousClose[index];
            indicesLastQuarterClose[index] = indicesEndOfLastQuarter.get(index).get(0).getClose().doubleValue();
            indicesLastYearClose[index] = indicesEndOfLastYear.get(index).get(0).getClose().doubleValue();
            indicesExpectedQuarterToDateChange[index] = 100 * (indicesExpectedClose[index] - indicesLastQuarterClose[index]) / indicesLastQuarterClose[index];
            indicesExpectedYearToDateChange[index] = 100 * (indicesExpectedClose[index] - indicesLastYearClose[index]) / indicesLastYearClose[index];
        }


        // verifying that "Stock Trade Summary" values are correct
        for (int stock=0; stock<symbols.length; stock++){
            if (Math.abs(Double.parseDouble(findElements(weekClose).get(stock).getText()) - expectedClose[stock]) > 0.02){
                System.out.println(companies[stock]+"'s week close is inaccurate.\n\tExpected: "+expectedClose[stock]+"\n\tDisplayed: "+findElements(weekClose).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekChange).get(stock).getText()) - expectedWeekChange[stock]) > 0.02){
                System.out.println(companies[stock]+"'s week change is inaccurate.\n\tExpected: "+expectedWeekChange[stock]+"\n\tDisplayed: "+findElements(weekChange).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekChangeP).get(stock).getText()) - expectedWeekChangeP[stock]) > 0.02){
                System.out.println(companies[stock]+"'s week change % is inaccurate.\n\tExpected: "+expectedWeekChangeP[stock]+"\n\tDisplayed: "+findElements(weekChangeP).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekHigh).get(stock).getText()) - expectedHigh[stock]) > 0.02){
                System.out.println(companies[stock]+"'s week high price is inaccurate.\n\tExpected: "+expectedHigh[stock]+"\n\tDisplayed: "+findElements(weekHigh).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekLow).get(stock).getText()) - expectedLow[stock]) > 0.02){
                System.out.println(companies[stock]+"'s week low price is inaccurate.\n\tExpected: "+expectedLow[stock]+"\n\tDisplayed: "+findElements(weekLow).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekVolume).get(stock).getText())*10E5 - expectedVolume[stock]) > 20000){
                System.out.println("Known issue - ADMIN-399 - "+companies[stock]+"'s week volume is inaccurate.\n\tExpected: "+expectedVolume[stock]+"\n\tDisplayed: "+findElements(weekVolume).get(stock).getText()+" million");
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(averageWeekVolume).get(stock).getText())*10E5 - expectedVolumeWeekAverage[stock]) > 20000){
                System.out.println(companies[stock]+"'s average weekly volume is inaccurate.\n\tExpected: "+expectedVolumeWeekAverage[stock]+"\n\tDisplayed: "+findElements(averageWeekVolume).get(stock).getText()+" million");
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(volumeVariance).get(stock).getText()) - expectedVolumeVariance[stock]) > 0.02){
                if (100*(Double.parseDouble(findElements(weekVolume).get(stock).getText()) - Double.parseDouble(findElements(averageWeekVolume).get(stock).getText()))
                        /Double.parseDouble(findElements(averageWeekVolume).get(stock).getText()) - Double.parseDouble(findElements(volumeVariance).get(stock).getText()) < 0.02){
                    System.out.println(companies[stock]+"'s volume variance is inaccurate due to carryover from inaccurate week volume or/and average weekly volume.");
                    allValid = false;
                }
                else {
                    System.out.println(companies[stock]+"'s volume variance is inaccurate.\n\tExpected: "+expectedVolumeVariance[stock]+"\n\tDisplayed: "+findElements(volumeVariance).get(stock).getText());
                    allValid = false;
                }
            }
            if (Math.abs(Double.parseDouble(findElements(qtdChange).get(stock).getText()) - expectedQuarterToDateChange[stock]) > 0.02){
                System.out.println(companies[stock]+"'s QTD change is inaccurate.\n\tExpected: "+expectedQuarterToDateChange[stock]+"\n\tDisplayed: "+findElements(qtdChange).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(ytdChange).get(stock).getText()) - expectedYearToDateChange[stock]) > 0.02){
                System.out.println(companies[stock]+"'s YTD change is inaccurate.\n\tExpected: "+expectedYearToDateChange[stock]+"\n\tDisplayed: "+findElements(ytdChange).get(stock).getText());
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
            if (Math.abs(Double.parseDouble(findElements(indicesClose).get(index).getText().replace(",","")) - indicesExpectedClose[index]) > 5){
                System.out.println("Index "+indices[index]+"'s week close is inaccurate.\n\tExpected: "+indicesExpectedClose[index]+"\n\tDisplayed: "+findElements(indicesClose).get(index).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(indicesChange).get(index).getText().replace(",","")) - indicesExpectedWeekChange[index]) > 5){
                System.out.println("Index "+indices[index]+"'s week change is inaccurate.\n\tExpected: "+indicesExpectedWeekChange[index]+"\n\tDisplayed: "+findElements(indicesChange).get(index).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(indicesChangeP).get(index).getText()) - indicesExpectedWeekChangeP[index]) > 0.25){
                System.out.println("Index "+indices[index]+"'s week change % is inaccurate.\n\tExpected: "+indicesExpectedWeekChangeP[index]+"\n\tDisplayed: "+findElements(indicesChangeP).get(index).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(indicesQTDChange).get(index).getText()) - indicesExpectedQuarterToDateChange[index]) > 0.25){
                System.out.println("Index "+indices[index]+"'s QTD change is inaccurate.\n\tExpected: "+indicesExpectedQuarterToDateChange[index]+"\n\tDisplayed: "+findElements(indicesQTDChange).get(index).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(indicesYTDChange).get(index).getText()) - indicesExpectedYearToDateChange[index]) > 0.25){
                System.out.println("Index "+indices[index]+"'s YTD change is inaccurate.\n\tExpected: "+indicesExpectedYearToDateChange[index]+"\n\tDisplayed: "+findElements(indicesYTDChange).get(index).getText());
                allValid = false;
            }
        }


        return allValid;
    }
}
