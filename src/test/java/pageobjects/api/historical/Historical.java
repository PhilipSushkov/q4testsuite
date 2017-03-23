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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by easong on 2017-03-22.
 */

public class Historical extends util.Functions {
    private static String sPathToFileAuth, sDataFileAuthJson, sPathToFileHist, sDataFileHistJson;
    private static String host, app_ver, access_token, ticker, exchange, connection, user_agent, security_name, securityId;
    private static boolean result = false;
    private static boolean dataexists = true;
    private static JSONParser parser;
    private static HttpClient client;
    private static final String DEVELOP_ENV = "Develop_Env", SECURITIES = "Securities", PROTOCOL = "https://", HISTORICAL = "historical";
    private static int i, failurecount;
    private static String earliestDate;
    private static String q4DatabaseRequestDate;
    private static double YahooPrice, Q4Price;
    private static int numberOfDates  = 0;

    public Historical() throws IOException {
        parser = new JSONParser();

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);

        sPathToFileAuth = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Auth");
        sDataFileAuthJson = propAPI.getProperty("jsonData_Auth");
        sPathToFileHist = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Hist");
        sDataFileHistJson = propAPI.getProperty("jsonData_Hist");
        client = HttpClientBuilder.create().build();
    }
    public boolean compareHistoricalData() throws IOException {

        JSONObject jsonEnvData;
        JSONObject jsonEnv = new JSONObject();
        org.json.simple.JSONArray jsonHistData;
        org.json.JSONArray securityArray = new org.json.JSONArray();

        try {
            // reading in environment variables
            FileReader readAuthFile = new FileReader(sPathToFileAuth + sDataFileAuthJson);
            jsonEnvData = (org.json.simple.JSONObject) parser.parse(readAuthFile);
            jsonEnv = (JSONObject) jsonEnvData.get(DEVELOP_ENV);
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

        // this loop will run through every stock
        for (int securityCounter = 0; securityCounter < securityArray.length(); securityCounter++) {
            //System.out.println(iterator.next());
            // parse through each piece of the array and create a JSONObject for each
            org.json.JSONObject individualStock = securityArray.getJSONObject(securityCounter);
            security_name = individualStock.getString("company_name");
            ticker = individualStock.get("symbol").toString();
            exchange = individualStock.get("exchange").toString();
            securityId = individualStock.get("_id").toString();

        //System.out.println("Now checking: " + security_name + "       securityId: " + securityId);

        // Q4 retains 300 days of stock data from the current date
        // To get all 300 days of data from Yahoo to compare against Q4DB, we need the earliest date value in our DB to create a "from" parameter for a Yahoo Request
        // Obtaining earliest date in Q4 DB

        // API Request format: {{url}}/api/stock/historical?appver={{appver}}&securityID={{securityId}}
        String urlHistQuery = PROTOCOL + host + "/api/stock/historical?appver=" + app_ver + "&securityId=" + securityId;

        HttpGet get = new HttpGet(urlHistQuery);
        // Setting up authentication headers
        get.setHeader("User-Agent", user_agent);
        get.setHeader("Connection", connection);
        get.setHeader("Authorization", "Bearer " + access_token);

        HttpResponse response = client.execute(get);

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

                //System.out.println("Earliest date in Q4 Database is " + earliestDate + " for " + ticker);

                // Now we can create the Yahoo Finance Request with the earliestDate object

                // Converting the earliestDate String into a Yahoo Calendar Object
                DateFormat q4Format = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date earliestDateInYahooFormat = q4Format.parse(earliestDate);
                    java.util.Calendar earliestDateForYahoo = java.util.Calendar.getInstance();
                    earliestDateForYahoo.setTime(earliestDateInYahooFormat);

                    // HistoricalQuote is the main Yahoo API Request object
                    // initializing some fault values to satisfy constructor
                    long defaultLong = 0;
                    BigDecimal defaultBigDecimal = new BigDecimal("0");
                    HistoricalQuote lastTradingDayQuotes = new HistoricalQuote("default",earliestDateForYahoo,defaultBigDecimal,defaultBigDecimal,defaultBigDecimal,defaultBigDecimal,defaultBigDecimal,defaultLong);

                    // sending first request to yahoo to record number of days of data yahoo has from the earliestDate in our Database
                    // Yahoo's requests can be unstable. This for loop sends the same request back to yahoo 10 times before giving up and allowing the error to flow
                    for (failurecount = 0; failurecount < 10; failurecount++) {
                        try {
                            numberOfDates = YahooFinance.get(ticker).getHistory(earliestDateForYahoo, Interval.DAILY).size();
                            break;
                        } catch (Exception e) { }
                    }

                    // if yahoo's array has no content, then Yahoo is missing data
                    if ( failurecount == 10) {
                        System.out.println("Yahoo does not have data for " + ticker + " : " + exchange + "     securtiyID = " + securityId);
                        System.out.println("------");
                        }


                    if (numberOfDates < 290) { System.out.println("There are only " + numberOfDates + "of data for " + security_name + "       securityId: " + securityId); }

                    //now construct a for loop that will parse through each day of data from yahoo and compare against Q4
                    for (i = numberOfDates - 1 ; i > 0 && failurecount != 10; i-- ) {
                        // collecting each day of data from Yahoo

                        // Yahoo's requests can be unstable. This for loop sends the same request back to yahoo 10 times before giving up and allowing the error to flow
                        for (failurecount = 0; failurecount < 10; failurecount++) {
                           try {
                                lastTradingDayQuotes = YahooFinance.get("SW5").getHistory(earliestDateForYahoo, Interval.DAILY).get(i);
                                System.out.println("SW5 : " + lastTradingDayQuotes.toString());
                                break;
                           } catch (Exception e) { }
                        }

                        if ( failurecount == 10) {
                            System.out.println("Yahoo does not have data for " + ticker + " : " + exchange + "     securtiyID = " + securityId);
                            System.out.println("------");
                            break;
                        }

                        // saving Yahoo's end of day price
                        YahooPrice = lastTradingDayQuotes.getClose().doubleValue();
                        // print statement to see all Q4 prices
                        //System.out.println("Yahoo's price is " + YahooPrice);

                        // converting the date in yahoo's body to Q4 format
                        Date q4Date = new Date(lastTradingDayQuotes.getDate().getTime().toString());
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                        q4DatabaseRequestDate = formatter.format(q4Date);

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
                            org.json.JSONArray individualJSONArray  = individualJSONResponse.getJSONArray(HISTORICAL);

                            if (individualJSONResponse.getJSONArray("historical").length() != 0) {
                                dataexists = true;
                                for (int z = 0; z < individualJSONArray.length(); z++) {
                                    org.json.JSONObject jsonHistItem = individualJSONArray.getJSONObject(z);
                                    // print statement to see all Q4 prices
                                    // System.out.println("Q4 Desktop Price : " + jsonHistItem.get("Last").toString());
                                    Q4Price = Double.parseDouble(jsonHistItem.get("Last").toString());
                                }
                            } else {
                                // data doesn't exist for this day
                                System.out.println("Stock data doesn't exist for " + ticker + " : " + exchange + " on " + q4Date);
                                dataexists = false;
                            }
                        }
                        if (dataexists) {
                            // margin of error between the 2 stock prices
                            result = (Math.abs(YahooPrice - Q4Price) < 0.01);
                            // print error for stock and date
                            if (!result) {
                                System.out.println(ticker + " : " + exchange + " is inaccurate on " + q4Date);
                                System.out.println("Yahoo price: " + YahooPrice + " Q4 Price: " + Q4Price);
                                // divides each failure
                                System.out.println("------");
                            }
                        }
                    }

                } catch (java.text.ParseException e) {
                    System.out.println("Q4 Date Format Couldn't Be Parsed");
                }
            } else {
                // no data was found in the request
                System.out.println("Q4 Returned no historical stock data returned for " + ticker + " : " + exchange + "     security ID: " + securityId);
                System.out.println("------");
            }

        }


    } return result;

}

}

