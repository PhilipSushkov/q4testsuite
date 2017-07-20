package pageobjects.euroApi.analysis;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by easong on 4/11/17.
 */
public class EuroinvestorStockQuoteRT {

    // assume data is correct. If the data is found to be incorrect, this boolean will be adjusted to false
    private boolean realTimeDataIsCorrect = true;

    private int zeroDataCounter = 0;
    // looking for a non-zero object
    private boolean nonZeroObjectFound = false;

    private final JSONObject individualdata;
    private final HttpClient client;
    private String instrumentID;
    private String solutionID;
    private String customerKey;

    // Variables used to build requests
    private final String protocol = "http://";
    private final String Q4Host = "q4eurotestir.q4web.com";
    private final String euroInvestorHost = "ir.euroinvestor.com";
    private final double priceTolerancePercentage = 0.05;
    private final double timeToleranceInMilliseconds = 15000; // 15 seconds

    // error reporting variables
    private String Q4Request;
    private String euroInvestorRequest;
    private List<String> miscellaneousErrorList = new ArrayList<>();
    private List<String> missingDataList = new ArrayList<>();
    private List<String> inaccurateDataList = new ArrayList<>();


    public EuroinvestorStockQuoteRT(org.json.simple.JSONObject globalindividualdata) throws IOException {

        // creating a local copy of data from the DataProvider to avoid overlap
        individualdata = new JSONObject(globalindividualdata);

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
        client = HttpClientBuilder.create().build();
    }

    public void dataValidation() {

        try {

            // collecting essential data to build request
            customerKey = individualdata.get("CustomerKey").toString();
            solutionID = individualdata.get("SolutionID").toString();
            instrumentID = individualdata.get("IRInstrumentID").toString();

            // Sending the same request to both databases to obtain what should be 2 identical JSONArrays of Data
            JSONArray Q4Data = getStockQuoteArray(Q4Host);
            JSONArray EuroInvestorData = getStockQuoteArray(euroInvestorHost);

            // checking if Euroinvestor returned an array of data
            if (EuroInvestorData == null) {
                // no data was found in the request, storing error in list
                miscellaneousErrorList.add("Euroinvestor Returned no data");
                realTimeDataIsCorrect = false;
                return;
            }

            // checking if Q4 returned an array of data
            if (Q4Data == null) {
                // no data was found in the request, storing error in list
                miscellaneousErrorList.add("Q4 Returned no data");
                realTimeDataIsCorrect = false;
                return;
            }

            //comparing data between the two arrays
            compareData(Q4Data, EuroInvestorData);

        } catch (Exception e) {
        }
    }

    private void compareData(JSONArray q4Data, JSONArray euroInvestorData) {


        // parsing through both arrays at the same time
        for (int arrayCounter = 0; arrayCounter < euroInvestorData.length(); ++arrayCounter) {

            // boolean to track matching within the Q4Array
            boolean foundMatch = false;

            // collecting individual storyIDs from individual JSONObjects
            org.json.JSONObject EuroInvestorQuote = euroInvestorData.getJSONObject(arrayCounter);
            double euroInvestorLastestPrice = Double.parseDouble(EuroInvestorQuote.get("last").toString());
            String euroInvestorExchange = EuroInvestorQuote.get("exchangeName").toString();
            String euroInvestorName = EuroInvestorQuote.get("name").toString();
            String euroInvestorCurrency = EuroInvestorQuote.get("currency").toString();
            String euroInvestorTradeTimeStamp = EuroInvestorQuote.get("tradeTimestamp").toString();

            // this catches response objects that contain no data
            if (!euroInvestorExchange.equals("exchange")) {

                // found a non-zero object means that the quote actually returns legit data
                nonZeroObjectFound = true;

                for (int Q4ArrayCounter = 0; Q4ArrayCounter < q4Data.length(); Q4ArrayCounter++) {

                    // parsing through Q4Array trying to find the same storyID
                    org.json.JSONObject Q4Quote = q4Data.getJSONObject(Q4ArrayCounter);

                    double Q4LastestPrice = Double.parseDouble(Q4Quote.get("last").toString());
                    String Q4Exchange = Q4Quote.get("exchangeName").toString();
                    String Q4Name = Q4Quote.get("name").toString();
                    String Q4Currency = Q4Quote.get("currency").toString();
                    String Q4TradeTimeStamp = Q4Quote.get("tradeTimestamp").toString();

                    // comparing the exchanges to match equal quotes
                    if (euroInvestorExchange.equals(Q4Exchange) && euroInvestorName.equals(Q4Name) && euroInvestorCurrency.equals(Q4Currency)) {
                        // declaring that a match was found
                        foundMatch = true;

                        // first comparing reporting time
                        if ( Math.abs(toEpoch(Q4TradeTimeStamp) - toEpoch(euroInvestorTradeTimeStamp)) > timeToleranceInMilliseconds ) {
                            miscellaneousErrorList.add("Reporting time difference is too great at: " + Math.abs(toEpoch(Q4TradeTimeStamp) - toEpoch(euroInvestorTradeTimeStamp)) / 1000 + " seconds for exchange " + euroInvestorExchange);
                            realTimeDataIsCorrect = false;
                            break;
                        }

                        // checking for accuracy
                        if ( ( Math.abs(Q4LastestPrice - euroInvestorLastestPrice) ) / euroInvestorLastestPrice > priceTolerancePercentage) {
                            // this security has inaccurate data
                            realTimeDataIsCorrect = false;
                            // recording inaccuracy
                            inaccurateDataList.add("Inaccuracy: Q4 price is " + Q4LastestPrice + " EuroInvestor Price is " + euroInvestorLastestPrice + " for " + Q4Name);
                        }
                        // match found, loop should end now
                        break;
                    }
                }

                if (!foundMatch) {
                    // this security is missing news data
                    realTimeDataIsCorrect = false;
                    missingDataList.add("Q4 is missing real time data for exchange: " + EuroInvestorQuote.get("exchangeName").toString() + " security: " + EuroInvestorQuote.get("name").toString() + " on " + EuroInvestorQuote.get("timestamp").toString());
                }

            } else {
                zeroDataCounter++;
            }
        }

        if (zeroDataCounter != 0 && !nonZeroObjectFound) {
            // this catches response objects that contain no data
            missingDataList.add("Euroinvestor returned only empty objects");
        }
    }

    private JSONArray getStockQuoteArray(String host) {

        try {

            // Sample API Request - http://ir.euroinvestor.com/ServiceEngine/api/RequestStockDataBundleRT?apiversion=1&customerkey=aquaticfoodsgroupplc&instrumentid=1000028&solutionid=1880&format=json
            String Q4RequestQuery = protocol + host + "/ServiceEngine/api/RequestStockDataBundleRT?apiversion=1&customerkey=" + customerKey + "&instrumentid=" + instrumentID + "&solutionid=" + solutionID + "&format=json";

            // recording request strings
            if (host.equals(Q4Host)){
                Q4Request = Q4RequestQuery;
            } else {
                euroInvestorRequest = Q4RequestQuery;
            }

            // System.out.println( individualdata.get("symbol").toString() + " = " + Q4RequestQuery);

            // sending and recording request
            HttpGet get = new HttpGet(Q4RequestQuery);
            HttpResponse response = client.execute(get);

            // parsing through response data
            if (response.getStatusLine().getStatusCode() == 200) {
                // converting the HttpResponse Object into a string
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                // creating a JSONArray within the data object
                org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
                JSONArray jsonArray = jsonResponse.optJSONArray("data");

                return jsonArray;

            } else {
                // initial request to database was not successful, storing error in list
                miscellaneousErrorList.add("Response code from " + host + " was " + response.getStatusLine().getStatusCode());
                miscellaneousErrorList.add("Failed link: " + Q4RequestQuery);
                realTimeDataIsCorrect = false;
            }

        } catch (Exception e) {
        }

        return null;
    }


    private long toEpoch(String Date) {

        Date = Date.replace("0000","");

        // sample returned date format 2017-04-05T16:25:47.0000000Z
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS");

        // using epoch time to convert lengths
        long epoch = 0;
        java.util.Date date = null;

        try {
            date = dateFormat.parse(Date);
            epoch = date.getTime();

        } catch (Exception e) { System.out.println("could not parse " + Date );}

        return epoch;
    }

    public boolean isRealTimeDataIsCorrect() {

        return realTimeDataIsCorrect;
    }

    public List<String> getMissingDataList() {
        return missingDataList;
    }

    public List<String> getInaccurateDataList() {
        return inaccurateDataList;
    }

    public List<String> getMiscellaneousErrorList() {

        // adding request failures
        miscellaneousErrorList.add("Q4 Request = <a href=\""+Q4Request+"\" target=\"_blank\">"+Q4Request+"</a>");
        miscellaneousErrorList.add("Euroinvestor Request =  <a href=\""+euroInvestorRequest+"\" target=\"_blank\">"+euroInvestorRequest+"</a>" );
        return miscellaneousErrorList;
    }


}
