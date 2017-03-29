package pageobjects.api.historical;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by easong on 3/23/17.
 */
public class HistoricalStockQuote {

    private int i;
    private  int failurecount;
    private  String earliestDate = "";
    private  String q4DatabaseRequestDate;
    private  double YahooPrice;
    private  double Q4Price = 0;
    private  int numberOfDates = 0;
    private  String ticker;
    private  String exchange;
    private  String security_name;
    private  String securityId;
    private  boolean individualstockresult = true;
    private  boolean requestSuccess;
    private  int securityCounter;
    private  String Q4Currency;

    private  String host;
    private  String app_ver;
    private  String access_token;
    private  String connection;
    private  String user_agent;
    private  boolean result = false;
    private  boolean dataexists = true;
    private  HttpClient client;
    private  final String DEVELOP_ENV = "Develop_Env", SECURITIES = "Securities", PROTOCOL = "https://", HISTORICAL = "historical";
    private  org.json.JSONArray securityArray = new org.json.JSONArray();
    ArrayList<String> accurateCompanies = new ArrayList<String>();

    private  HistoricalQuote lastTradingDayQuotes;
    private  java.util.Calendar earliestDateForYahoo;
    private  HttpResponse response;
    private  DateFormat q4Format;
    private  Date q4Date;
    private JSONObject individualdata;

    public HistoricalStockQuote(JSONObject globalindividualdata, String environment) throws IOException {

    individualdata = new JSONObject(globalindividualdata);

    // setup all environment variables. JSON file locations, Q4 API Permissions, and initialize yahoo object

    // creating paths to the JSON files
    String sPathToFileAuth, sDataFileAuthJson, sPathToFileHist, sDataFileHistJson;

    JSONParser parser = new JSONParser();
    JSONObject jsonEnvData;
    JSONObject jsonEnv = new JSONObject();
    org.json.simple.JSONArray jsonHistData;

    //To hide warnings logs from execution console.
    Logger logger = Logger.getLogger("");
    logger.setLevel(Level.OFF);

    sPathToFileAuth = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Auth");
    sDataFileAuthJson = propAPI.getProperty("jsonData_Auth");
    sPathToFileHist = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Hist");
    sDataFileHistJson = propAPI.getProperty("jsonData_Hist");
    client = HttpClientBuilder.create().build();

    try {
        // reading in environment variables
        FileReader readAuthFile = new FileReader(sPathToFileAuth + sDataFileAuthJson);
        jsonEnvData = (org.json.simple.JSONObject) parser.parse(readAuthFile);
        jsonEnv = (JSONObject) jsonEnvData.get(environment);
        // reading in stock data
        FileReader readHistFile = new FileReader(sPathToFileHist + sDataFileHistJson);
        // creating an array of all stocks
        jsonHistData = (org.json.simple.JSONArray) parser.parse(readHistFile);
        securityArray = new JSONArray(jsonHistData.toString());
    } catch (ParseException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    host = jsonEnv.get("host").toString();
    app_ver = jsonEnv.get("app_ver").toString();
    access_token = jsonEnv.get("access_token").toString();
    user_agent = jsonEnv.get("user_agent").toString();
    connection = jsonEnv.get("connection").toString();

    }

    public void dataValidation() {

        try {
            // this boolean keep track of the data health for individual stocks
            individualstockresult = true;
            requestSuccess = true;

            // org.json.JSONObject individualStock = securityArray.getJSONObject(securityCounter);
            security_name = individualdata.get("company_name").toString();
            ticker = individualdata.get("symbol").toString();
            exchange = individualdata.get("exchange").toString();
            securityId = individualdata.get("_id").toString();

            // Q4 retains 300 days of stock data from the current date
            // To get all 300 days of data from Yahoo to compare against Q4DB, we need the earliest date value in our DB to create a "from" parameter for a Yahoo Request
            sendStockQuoteRequestToQ4DB();

            // Obtaining earliest date in Q4 DB
            // ensuring request was successful
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                String responseBody = EntityUtils.toString(entity);
                org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
                // Creating array of stock data where each day is the next index
                org.json.JSONArray historicalArray = jsonResponse.getJSONArray(HISTORICAL);
                // Checking if data exists
                if (jsonResponse.getJSONArray("historical").length() != 0) {
                    // read through Historical Stock Data for final entry
                    for (int j = 0; j < historicalArray.length(); j++) {
                        org.json.JSONObject jsonHistItem = historicalArray.getJSONObject(j);
                        // recording the date property
                        earliestDate = jsonHistItem.get("Date").toString();
                    }
                } else {
                    // no data was found in the request
                    System.out.println("Q4 Returned no historical stock data for " + ticker + " : " + exchange + "     security ID: " + securityId);
                    System.out.println("------");
                    individualstockresult = false;
                }

            } else {
                // initial request to database was not successful
                System.out.println("Response code from Q4 Database was not 200, it was " + response.getStatusLine().getStatusCode());
                individualstockresult = false;
            }

            System.out.println("Earliest date in Q4 Database is " + earliestDate + " for " + ticker);
            // Now we can create the Yahoo Finance Request with the earliestDate object
            getYahooData();


        } catch (IOException e) {
        }
    }

    public void sendStockQuoteRequestToQ4DB() {

        try {


        // API Request format: {{url}}/api/stock/historical?appver={{appver}}&securityID={{securityId}}
        String urlHistQuery = PROTOCOL + host + "/api/stock/historical?appver=" + app_ver + "&securityId=" + securityId;
        System.out.println("Q4 query = " + urlHistQuery);

        HttpGet get = new HttpGet(urlHistQuery);
        // Setting up authentication headers
        get.setHeader("User-Agent", user_agent);
        get.setHeader("Connection", connection);
        get.setHeader("Authorization", "Bearer " + access_token);

        response = client.execute(get);
        } catch (IOException e) {}

    }

    public boolean checkrequestfailure(int failcount, String ticker, String exchange, String securityId) {

        // loop will fail after 10 runs
        if (failcount == 10) {
            // printing error
            System.out.println("Yahoo encountered an error while requesting for " + ticker + " : " + exchange + "     securtiyID = " + securityId);
            System.out.println("------");
            // stock check failed
            individualstockresult = false;
            return false;
        } else {
            // request will keep going
            return true;
        }
    }

    void getYahooData() {

    // HistoricalQuote is the main Yahoo API Request object
    // initializing some fault values to satisfy constructor
    // long defaultLong = 0;
    // BigDecimal defaultBigDecimal = new BigDecimal("0");
    earliestDateForYahoo = java.util.Calendar.getInstance();

    // Converting Q4 tickers to the Yahoo Format based on exchange
    adjustQueryForYahoo();

        try {
            // Converting the earliestDate String into a Yahoo Calendar Object
            q4Format = new SimpleDateFormat("MM/dd/yyyy");
            Date earliestDateInYahooFormat = q4Format.parse(earliestDate);
            earliestDateForYahoo.setTime(earliestDateInYahooFormat);

            // sending first request to yahoo to record number of days of data yahoo has from the earliestDate in our Database
            // Yahoo's requests can be unstable. This for loop sends the same request back to yahoo 10 times before giving up and allowing the error to end the test for the current ticker
            for (failurecount = 0; requestSuccess; failurecount++) {
                try {
                    numberOfDates = YahooFinance.get(ticker).getHistory(earliestDateForYahoo, Interval.DAILY).size();
                    break;
                } catch (Exception e) {
                    requestSuccess = checkrequestfailure(failurecount, ticker, exchange, securityId);
                }
            }

            //break if previous request failed
            if (!requestSuccess) {
                return;
            }

            // checks if we have less than 300 days of data for a security
            if (numberOfDates < 290) {
                System.out.println("There are only " + numberOfDates + " days of data for " + security_name + "       securityId: " + securityId);
            }

        } catch (java.text.ParseException e) {
            System.out.println("Q4 Date Format Couldn't Be Parsed");
        }

        //now construct a for loop that will parse through each day of data from yahoo and compare against Q4
        for (i = numberOfDates - 1; i > 0 && failurecount != 10; i--) {
            // collecting each day of data from Yahoo
                for (failurecount = 0; requestSuccess; failurecount++) {
                    try {
                        lastTradingDayQuotes = YahooFinance.get(ticker).getHistory(earliestDateForYahoo, Interval.DAILY).get(i);
                        break;
                    } catch (Exception e) {
                        requestSuccess = checkrequestfailure(failurecount, ticker, exchange, securityId);
                    }
                }

        //break if previous request failed
        if (!requestSuccess) {
            break;
        }

        // saving Yahoo's end of day price
        YahooPrice = lastTradingDayQuotes.getClose().doubleValue();
        // print statement to see all Q4 prices
        // System.out.println("Yahoo's price is " + YahooPrice);

        // converting the date in yahoo's body to Q4 format
        q4Date = new Date(lastTradingDayQuotes.getDate().getTime().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        q4DatabaseRequestDate = formatter.format(q4Date);

        // collect data from Q4's DB with Yahoo's date
        collectQ4Data();

        // adjust for currency between the 2 DBs
        adjustResultsForYahoo();

            if (dataexists) {
                // compares data between the 2 results
                compareData();
            }
        }
    }

    private void adjustResultsForYahoo() {

        if (Objects.equals(exchange,"XLON")) {
            // Adjust Q4 Currency from GBP to Pound Sterling
            Q4Price = Q4Price * 100;

            // Accounting for the differences in Yahoo's currency. Some are off by a factor of 100x due to currency
            if (90 < Q4Price / YahooPrice && Q4Price / YahooPrice < 110) {
                YahooPrice = YahooPrice * 100;
            }
        }
    }

    private void adjustQueryForYahoo() {
        // Adjusting query format for Yahoo
        if (Objects.equals(exchange,"XLON")) {
            // .L is the format for london stock exchange for Yahoo
            ticker = ticker + ".L";
        }
    }

    private boolean compareData() {
        // margin of error between the 2 stock prices
        result = (Math.abs(YahooPrice - Q4Price) < 0.01);
        // print error for stock and date
        if (!result) {
            // this stock was found to have at least one error
            individualstockresult = false;
            System.out.println(ticker + " : " + exchange + " is inaccurate on " + q4Date);
            System.out.println("Yahoo price: " + YahooPrice + " Q4 Price: " + Q4Price);
            // divides each failure
            System.out.println("------");
        }

        return result;

    }

    private void collectQ4Data() {

        try {

            // Collecting Q4Data for that date
            String q4DataBaseIndividualRequest = PROTOCOL + host + "/api/stock/historical?appver=" + app_ver + "&securityId=" + securityId + "&startDate=" + q4DatabaseRequestDate + "&endDate=" + q4DatabaseRequestDate;
            // Setting up new requests
            HttpGet getIndividual = new HttpGet((q4DataBaseIndividualRequest));
            // Setting up authentication headers for individual get query
            getIndividual.setHeader("User-Agent", user_agent);
            getIndividual.setHeader("Connection", connection);
            getIndividual.setHeader("Authorization", "Bearer " + access_token);
            HttpResponse individualResponse = client.execute(getIndividual);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity individualEntity = individualResponse.getEntity();
                String individualResponseBody = EntityUtils.toString(individualEntity);
                org.json.JSONObject individualJSONResponse = new org.json.JSONObject(individualResponseBody);
                org.json.JSONArray individualJSONArray = individualJSONResponse.getJSONArray(HISTORICAL);

                if (individualJSONResponse.getJSONArray("historical").length() != 0) {
                    dataexists = true;
                    for (int z = 0; z < individualJSONArray.length(); z++) {
                        org.json.JSONObject jsonHistItem = individualJSONArray.getJSONObject(z);
                        // print statement to see all Q4 prices
                        // System.out.println("Q4 Desktop Price : " + jsonHistItem.get("Last").toString() + " on " + jsonHistItem.get("Date").toString()  + " on thread " + Thread.currentThread().getId());
                        Q4Price = Double.parseDouble(jsonHistItem.get("Last").toString());
                        Q4Currency = jsonHistItem.get("Currency").toString();
                    }
                } else {
                    // data doesn't exist for this day
                    // this stock was found to have at least one error
                    individualstockresult = false;
                    System.out.println("Stock data doesn't exist for " + ticker + " : " + exchange + " on " + q4Date);
                    dataexists = false;
                }
            }
        }  catch (IOException e) {}
    }

    public boolean stockDataIsAccurate() {
        return individualstockresult ;
    }
}