package specs.euroApi.analysis;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.euroApi.analysis.*;
import specs.ApiAbstractSpec;
import specs.api.historical.ExtentManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class EuroInvestorDataValidation extends ApiAbstractSpec {

    private static String sPathToFileHist, sDataFileHistJson;
    private static JSONParser parser;
    private final int threadPoolSize = 15;

    private ExtentReports extent;

    @BeforeTest
    public void setUp() throws IOException {

        sPathToFileHist = System.getProperty("user.dir") + propAPI.getProperty("dataPath_EuroInvestor_Hist");
        sDataFileHistJson = propAPI.getProperty("jsonData_EuroInvestor_Hist");
        extent = ExtentManager.GetExtent();
        System.setProperty("Q4 web driver", "Not quite sure what this is");
    }

    @DataProvider(parallel = true)
    public Object[][] IntradayData(Method method) {

        parser = new JSONParser();

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileHist + sDataFileHistJson));

            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < stockDataArray.size(); i++) {
                zoom.add(stockDataArray.get(i));
            }

            Object[][] stockData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                stockData[i][0] = zoom.get(i);
            }
            return stockData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DataProvider(parallel = true)
    public Object[][] StockData(Method method) {

        parser = new JSONParser();

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileHist + sDataFileHistJson));

            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < stockDataArray.size(); i++) {
                zoom.add(stockDataArray.get(i));
            }

            Object[][] stockData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                stockData[i][0] = zoom.get(i);
            }
            return stockData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DataProvider(parallel = true)
    public Object[][] NewsData(Method method) {

        parser = new JSONParser();

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileHist + sDataFileHistJson));

            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < stockDataArray.size(); i++) {
                zoom.add(stockDataArray.get(i));
            }

            Object[][] stockData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                stockData[i][0] = zoom.get(i);
            }
            return stockData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DataProvider(parallel = true)
    public Object[][] RealTimeStockData(Method method) {

        parser = new JSONParser();

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileHist + sDataFileHistJson));

            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < stockDataArray.size(); i++) {
                zoom.add(stockDataArray.get(i));
            }

            Object[][] stockData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                stockData[i][0] = zoom.get(i);
            }
            return stockData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DataProvider(parallel = true)
    public Object[][] DelayedData(Method method) {

        parser = new JSONParser();

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileHist + sDataFileHistJson));

            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < stockDataArray.size(); i++) {
                zoom.add(stockDataArray.get(i));
            }

            Object[][] stockData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                stockData[i][0] = zoom.get(i);
            }
            return stockData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DataProvider(parallel = true)
    public Object[][] IndexData(Method method) {

        parser = new JSONParser();

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileHist + sDataFileHistJson));

            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < stockDataArray.size(); i++) {
                zoom.add(stockDataArray.get(i));
            }

            Object[][] stockData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                stockData[i][0] = zoom.get(i);
            }
            return stockData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test(dataProvider = "StockData", threadPoolSize = threadPoolSize)
    public void CheckEuroStockDataHistorical(JSONObject data) throws IOException {

        ExtentTest test = extent.createTest("Stock data for" + "    " + data.get("symbol").toString() + " : " + data.get("exchangeName").toString());

        JSONObject individualdata = new JSONObject(data);

        EuroinvestorStockQuoteHistorical euroinvestorStockQuote = new EuroinvestorStockQuoteHistorical(individualdata);

        // validating data
        euroinvestorStockQuote.dataValidation();

        // checking data with the ExtentReport framework

        // checking if stock data is overall accurate
        if (euroinvestorStockQuote.stockDataIsPerfect()) {
            test.log(Status.PASS, "Stock data is accurate");
        } else {
            // checking for other related request errors
            for (String errorinstance : euroinvestorStockQuote.getMiscellaneousErrorList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // checking if stock data has zero values
            for (String errorinstance : euroinvestorStockQuote.getZeroDataList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // checking if stock data has inaccurate data
            for (String errorinstance : euroinvestorStockQuote.getInaccurateStockDataList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // checking if stock data is missing
            for (String errorinstance : euroinvestorStockQuote.getMissingDataList()) {
                test.log(Status.FAIL, errorinstance);
            }
        }
    }


    @Test(dataProvider = "NewsData", threadPoolSize = threadPoolSize)
    public void CheckEuroNewsData(JSONObject data) throws IOException {

        ExtentTest test = extent.createTest("News data for" + "    " + data.get("symbol").toString() + " : " + data.get("exchangeName").toString());

        JSONObject individualdata = new JSONObject(data);

        EuroinvestorNews euroinvestorNews = new EuroinvestorNews(individualdata);

        euroinvestorNews.dataValidation();

        // checking if news data is overall accurate
        if (euroinvestorNews.isNewsDataIsAccurate()) {
            // seeing if client is unsubscribed
            if (euroinvestorNews.getUnsubscribedClientList().size() != 0) {
                // printing client that is unsubscribed
                for (String errorinstance : euroinvestorNews.getUnsubscribedClientList()) {
                    test.log(Status.PASS, errorinstance);
                }
            } else {
                // client is subscribed and data is accurate
                test.log(Status.PASS, "News data is accurate");
            }

        } else {
            // printing miscellaneous errors
            for (String errorinstance : euroinvestorNews.getMiscellaneousErrorList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // printing missing news articles
            for (String errorinstance : euroinvestorNews.getInaccurateNewsList()) {
                test.log(Status.FAIL, errorinstance);
            }
        }
    }

    @Test(dataProvider = "DelayedData", threadPoolSize = threadPoolSize)
    public void CheckEuroDelayedData(JSONObject data) throws IOException {

        ExtentTest test = extent.createTest("Delayed data for" + "    " + data.get("symbol").toString() + " : " + data.get("exchangeName").toString());

        JSONObject individualdata = new JSONObject(data);

        EuroinvestorDelayed euroinvestorDelayed = new EuroinvestorDelayed(individualdata);

        euroinvestorDelayed.dataValidation();

        // checking if news data is overall accurate
        if (euroinvestorDelayed.isDelayedDataIsAccurate()) {
            test.log(Status.PASS, "Delayed data is accurate");
        } else {
            // printing miscellaneous errors
            for (String errorinstance : euroinvestorDelayed.getMiscellaneousErrorList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // printing missing news articles
            for (String errorinstance : euroinvestorDelayed.getInaccurateDataList()) {
                test.log(Status.FAIL, errorinstance);
            }
        }
    }


    @Test(dataProvider = "RealTimeStockData", threadPoolSize = threadPoolSize)
    public void CheckEuroRealTimeData(JSONObject data) throws IOException {

        ExtentTest test = extent.createTest("RealTime Stock data for" + "    " + data.get("symbol").toString() + " : " + data.get("exchangeName").toString());

        JSONObject individualdata = new JSONObject(data);

        EuroinvestorStockQuoteRT euroinvestorStockQuoteRT = new EuroinvestorStockQuoteRT(individualdata);

        euroinvestorStockQuoteRT.dataValidation();

        // checking if news data is overall accurate
        if (euroinvestorStockQuoteRT.isRealTimeDataIsCorrect()) {
            test.log(Status.PASS, "Real Time data is accurate");

            // printing URLs
            for (String nonerrors : euroinvestorStockQuoteRT.getMiscellaneousErrorList()) {
                test.log(Status.PASS, nonerrors);
            }

        } else {

            // printing miscellaneous errors
            for (String errorinstance : euroinvestorStockQuoteRT.getMiscellaneousErrorList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // printing missing stock quotes
            for (String errorinstance : euroinvestorStockQuoteRT.getMissingDataList()) {
                test.log(Status.FAIL, errorinstance);

            }
            // printing inaccurate stock quotes
            for (String errorinstance : euroinvestorStockQuoteRT.getInaccurateDataList()) {
                test.log(Status.FAIL, errorinstance);

            }
        }
    }

    @Test(dataProvider = "IntradayData", threadPoolSize = threadPoolSize)
    public void CheckEuroIntradayData(JSONObject data) throws IOException {

        ExtentTest test = extent.createTest("Intraday data for" + "    " + data.get("symbol").toString() + " : " + data.get("exchangeName").toString());

        JSONObject individualdata = new JSONObject(data);

        EuroinvestorIntraday euroinvestorIntraday = new EuroinvestorIntraday(individualdata);

        euroinvestorIntraday.dataValidation();

        // checking if news data is overall accurate
        if (euroinvestorIntraday.stockDataIsPerfect()) {
            test.log(Status.PASS, "Intraday data is accurate");

        } else {

            // printing miscellaneous errors
            for (String errorinstance : euroinvestorIntraday.getMiscellaneousErrorList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // printing missing stock quotes
            for (String errorinstance : euroinvestorIntraday.getMissingDataList()) {
                test.log(Status.FAIL, errorinstance);

            }
            // printing inaccurate stock quotes
            for (String errorinstance : euroinvestorIntraday.getInaccurateStockDataList()) {
                test.log(Status.FAIL, errorinstance);

            }
        }
    }

    @Test(dataProvider = "IndexData", threadPoolSize = threadPoolSize)
    public void CheckIndexData(JSONObject data) throws IOException {

        ExtentTest test = extent.createTest("Index Data data for" + "    " + data.get("symbol").toString() + " : " + data.get("exchangeName").toString());

        JSONObject individualdata = new JSONObject(data);

        EuroinvestorIndex euroinvestorIndex = new EuroinvestorIndex(individualdata);

        euroinvestorIndex.dataValidation();

        // checking if Index data is overall accurate
        if (euroinvestorIndex.IndexDataIsPerfect()) {
            test.log(Status.PASS, "Index data is accurate");
        } else {
            // printing miscellaneous errors
            for (String errorinstance : euroinvestorIndex.getMiscellaneousErrorList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // printing missing Index articles
            for (String errorinstance : euroinvestorIndex.getMissingQuoteList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // printing missing Quotes articles
            for (String errorinstance : euroinvestorIndex.getMissingArrayList()) {
                test.log(Status.FAIL, errorinstance);
            }
            // printing inaccurate Quotes articles
            for (String errorinstance : euroinvestorIndex.getInaccurateIndexDataList()) {
                test.log(Status.FAIL, errorinstance);
            }
        }
    }

    @AfterClass
    public void teardown() {

        // this prints out test results to the extentreport.html file
        extent.flush();
    }
}