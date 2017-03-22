package pageobjects.api.historical;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
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
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by easong on 3/21/17.
 */

public class HistoricalTest extends util.Functions {
    private static String sPathToFileAuth, sDataFileAuthJson, sPathToFileHist, sDataFileHistJson;
    private static String host, app_ver, access_token, ticker, exchange, connection, user_agent, security_name;
    private static boolean result = false;
    private static boolean dataexists = true;
    private static JSONParser parser;
    private static HttpClient client;
    private static final String STAGING_ENV = "Staging_Env", SECURITIES = "Securities", PROTOCOL = "https://", HISTORICAL = "historical";
    private static int i;
    private static String earliestDate;
    private static String q4DatabaseRequestDate;
    private static double YahooPrice, Q4Price;
    private static int numberOfDates;

    public HistoricalTest() throws IOException {
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
        JSONObject jsonHistData = new JSONObject();
        JSONArray securityArray = new JSONArray();
        JSONObject jsonHist;

        try {
            FileReader readAuthFile = new FileReader(sPathToFileAuth + sDataFileAuthJson);
            jsonEnvData = (JSONObject) parser.parse(readAuthFile);
            jsonEnv = (JSONObject) jsonEnvData.get(STAGING_ENV);

            FileReader readHistFile = new FileReader(sPathToFileHist + sDataFileHistJson);
            jsonHistData = (JSONObject) parser.parse(readHistFile);
            securityArray = (JSONArray) jsonHistData.get(SECURITIES);
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

        for (Iterator<String> iterator = securityArray.iterator(); iterator.hasNext(); securityArray.size()) {
            //System.out.println(iterator.next());
            security_name = iterator.next();
            jsonHist = (JSONObject) jsonHistData.get(security_name);
            if (Boolean.parseBoolean(jsonHist.get("do_assertions").toString())) {
                ticker = jsonHist.get("ticker").toString();
                exchange = jsonHist.get("exchange").toString();
            }
        }

        // Q4 retains 300 days of stock data from the current date
        // To get all 300 days of data from Yahoo to compare against Q4DB, we need the earliest date value in our DB to create a "from" parameter for a Yahoo Request
        // Obtaining earliest date in Q4 DB

        // API Request format: {{url}}/api/stock/historical?appver={{appver}}&exchange={{Exchange}}&symbol={{Ticker}}
        String urlHistQuery = PROTOCOL + host + "/api/stock/historical?appver=" + app_ver + "&exchange=" + exchange + "&symbol=" + ticker;

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

                System.out.println("Earliest date in Q4 Database is " + earliestDate + " for " + ticker);

                // Now we can create the Yahoo Finance Request with the earliestDate object
                // HistoricalQuote is the main Yahoo API Request object
                HistoricalQuote lastTradingDayQuotes;

                // Converting the earliestDate String into a Yahoo Calendar Object
                DateFormat q4Format = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        Date earliestDateInYahooFormat = q4Format.parse(earliestDate);
                        java.util.Calendar earliestDateForYahoo = java.util.Calendar.getInstance();
                        earliestDateForYahoo.setTime(earliestDateInYahooFormat);
                        // sending first request to yahoo to record number of days of data yahoo has from the earliestDate in our Database
                        numberOfDates = YahooFinance.get(ticker).getHistory(earliestDateForYahoo, Interval.DAILY).size();

                        //now construct a for loop that will parse through each day of data from yahoo and compare against Q4
                        for (i = numberOfDates; i > 0; i-- ) {
                            // collecting each day of data from Yahoo
                            lastTradingDayQuotes =  YahooFinance.get(ticker).getHistory(Interval.DAILY).get(i);

                            // saving Yahoo's end of day price
                            YahooPrice = lastTradingDayQuotes.getClose().doubleValue();
                            System.out.println("Yahoo's price is " + YahooPrice);

                            // converting the date in yahoo's body to Q4 format
                            Date q4Date = new Date(lastTradingDayQuotes.getDate().getTime().toString());
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            q4DatabaseRequestDate = formatter.format(q4Date);

                            // Collecting Q4Data for that date
                            String q4DataBaseIndividualRequest = PROTOCOL + host + "/api/stock/historical?appver=" + app_ver + "&exchange=" + exchange + "&symbol=" + ticker + "&startDate=" + q4DatabaseRequestDate + "&endDate=" + q4DatabaseRequestDate;
                            System.out.println(q4DataBaseIndividualRequest);
                            response = client.execute(get);

                            if (response.getStatusLine().getStatusCode() == 200) {

                                entity = response.getEntity();
                                responseBody = EntityUtils.toString(entity);
                                jsonResponse = new org.json.JSONObject(responseBody);
                                historicalArray = jsonResponse.getJSONArray(HISTORICAL);

                                if (jsonResponse.getJSONArray("historical").length() != 0) {
                                    dataexists = true;
                                    for (int z = 0; z < historicalArray.length() - 1; z++) {
                                        org.json.JSONObject jsonHistItem = historicalArray.getJSONObject(z);
                                        System.out.println("Q4 Desktop: " + jsonHistItem.get("Last").toString());
                                        Q4Price = Double.parseDouble(jsonHistItem.get("Last").toString());
                                    }
                                } else {
                                    // data doesn't exist for this day
                                    System.out.println("Stock data doesn't exist for " + ticker + " on " + q4Date);
                                    dataexists = false;
                                }
                            }
                            if (dataexists) {
                                result = (Math.abs(YahooPrice - Q4Price) < 100000);
                            }
                        }
                        // divides each test
                        System.out.println("------");

                    } catch (java.text.ParseException e) {
                            System.out.println("Q4 Date Format Couldn't Be Parsed");
                    }
                } else {
                // no data was found in the request
                System.out.println("No historical stock data returned for " + ticker);
            }

        }

        return result ;
    }

}


