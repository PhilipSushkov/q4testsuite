package pageobjects.api.historical;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class Historical extends util.Functions {
    private static String sPathToFileAuth, sDataFileAuthJson, sPathToFileHist, sDataFileHistJson;
    private static String host, app_ver, access_token, ticker, exchange, connection, user_agent, security_name;
    private static boolean result = true;
    private static JSONParser parser;
    private static HttpClient client;
    private static final String STAGING_ENV = "Staging_Env", SECURITIES = "Securities", PROTOCOL = "https://", HISTORICAL = "historical";
    private static int i;

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

                // {{url}}/api/stock/historical?appver={{appver}}&exchange={{Exchange}}&symbol={{Ticker}}
                String urlHistQuery = PROTOCOL + host + "/api/stock/historical?appver=" + app_ver + "&exchange=" + exchange + "&symbol=" + ticker;
                System.out.println(urlHistQuery);

                HttpGet get = new HttpGet(urlHistQuery);
                get.setHeader("User-Agent", user_agent);
                get.setHeader("Connection", connection);
                get.setHeader("Authorization", "Bearer " + access_token);

                HttpResponse response = client.execute(get);

                if(response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();

                    if (entity != null) {
                        String responseBody = EntityUtils.toString(entity);
                        try {
                            JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
                            //System.out.println(jsonResponse.toJSONString());

                            JSONArray historicalArray = (JSONArray) jsonResponse.get(HISTORICAL);
                            i = 0;
                            for (Iterator<JSONObject> iteratorHis = historicalArray.iterator(); iteratorHis.hasNext(); historicalArray.size()) {
                                JSONObject jsonHistItem = iteratorHis.next();
                                System.out.println(jsonHistItem.get("Date").toString());
                                System.out.println("Q4 Desktop: " + jsonHistItem.get("Last").toString());

                                // fetching data from Yahoo
                                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                                HistoricalQuote lastTradingDayQuotes;
                                try {
                                    Date newDate = df.parse(jsonHistItem.get("Date").toString());

                                    Calendar from = Calendar.getInstance();
                                    from.setTime(newDate);
                                    Calendar to = Calendar.getInstance();
                                    to.setTime(newDate);

                                    lastTradingDayQuotes = YahooFinance.get(ticker).getHistory(from, to, Interval.DAILY).get(0);
                                    System.out.println("Yahoo Finance: " + lastTradingDayQuotes.getClose());

                                    result = (Math.abs(lastTradingDayQuotes.getClose().doubleValue() - Double.parseDouble(jsonHistItem.get("Last").toString())) < 0.00001);
                                    System.out.println(result);

                                    if (!result) {
                                        break;
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return false;
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("------");
                                i++;

                                if (i>25) {
                                    break;
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }

            System.out.println(security_name + " has been checked");

        }

        return result;
    }

}
