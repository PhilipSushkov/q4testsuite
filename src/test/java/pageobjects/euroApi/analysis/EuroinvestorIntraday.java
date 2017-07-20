package pageobjects.euroApi.analysis;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by easong on 4/24/17.
 */
public class EuroinvestorIntraday {

    // assume data is correct. If the data is found to be incorrect, this boolean will be adjusted to false
    private boolean individualstockresult = true;

    private final JSONObject individualdata;
    private final HttpClient client;
    private String instrumentID;
    private String solutionID;
    private String customerKey;

    // Variables used to build requests
    private final String protocol = "http://";
    private final String Q4Host = "q4eurotestir.q4web.com";
    private final String euroInvestorHost = "ir.euroinvestor.com";
    private final int numberofDays = 5;
    private final double priceTolerancePercentage = 0.05;


    // error reporting variables
    private String Q4Request;
    private String euroInvestorRequest;
    private List<String> miscellaneousErrorList = new ArrayList<>();
    private List<String> inaccurateDataList = new ArrayList<>();
    private List<String> missingStockDataList = new ArrayList<>();
    private List<String> unsubscribedClientList = new ArrayList<>();

    public EuroinvestorIntraday(JSONObject globalindividualdata) throws IOException {

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
            JSONArray Q4Data = getIntradayArray(Q4Host);
            JSONArray EuroInvestorData = getIntradayArray(euroInvestorHost);


            // checking if Euroinvestor returned an array of data
            if (EuroInvestorData == null || EuroInvestorData.toString().equals("[]")) {
                // If no data is returned, we assume the client is not subscribed to orderdepth data
                unsubscribedClientList.add("Euroinvestor Returned no data");
                return;
            }

            // checking if Q4 returned an array of data
            if (Q4Data == null || Q4Data.toString().equals("[]")) {
                // no data was found in the request, storing error in list
                miscellaneousErrorList.add("Q4 Returned no data");
                individualstockresult = false;
                return;
            }

            //comparing data between the two arrays
            compareData(Q4Data, EuroInvestorData);

        } catch (Exception e) {
        }
    }

    private void compareData(JSONArray q4Data, JSONArray euroInvestorData) {

        // parsing through both arrays at the same time
        for (int arrayCounter = 0; arrayCounter < euroInvestorData.length(); arrayCounter++) {

            // boolean to track matching within the Q4Array
            boolean foundMatch = false;

            // collecting individual storyIDs from individual JSONObjects
            org.json.JSONObject EuroInvestorQuote = euroInvestorData.getJSONObject(arrayCounter);
            String euroTimeStamp = EuroInvestorQuote.get("timestamp").toString();
            double euroOpenPrice = Double.parseDouble(EuroInvestorQuote.get("open").toString());
            double euroHighPrice = Double.parseDouble(EuroInvestorQuote.get("high").toString());
            double EuroLowPrice =  Double.parseDouble(EuroInvestorQuote.get("low").toString());
            double EuroClosePrice = Double.parseDouble(EuroInvestorQuote.get("open").toString());

            for (int Q4ArrayCounter = 0; Q4ArrayCounter < q4Data.length(); Q4ArrayCounter++) {

                // collecting individual storyIDs from individual JSONObjects
                org.json.JSONObject Q4Quote = q4Data.getJSONObject(arrayCounter);
                String Q4TimeStamp = Q4Quote.get("timestamp").toString();
                double Q4OpenPrice = Double.parseDouble(Q4Quote.get("open").toString());
                double Q4HighPrice = Double.parseDouble(Q4Quote.get("high").toString());
                double Q4LowPrice =  Double.parseDouble(Q4Quote.get("low").toString());
                double Q4ClosePrice = Double.parseDouble(Q4Quote.get("open").toString());

                // comparing the dates
                if (Q4TimeStamp.equals(euroTimeStamp)) {
                    // declaring that a match was found
                    foundMatch = true;

                    // comparison tests
                    if ( ((Math.abs(euroOpenPrice - Q4OpenPrice) / Q4OpenPrice ) > priceTolerancePercentage) ) {
                        // this stock was found to have at least one error
                        individualstockresult = false;
                        inaccurateDataList.add("Q4 open price: " + euroOpenPrice + " EuroInvestor Price: " + euroOpenPrice + " on " + euroTimeStamp);
                    }

                    if ( ((Math.abs(euroHighPrice - Q4HighPrice) / Q4HighPrice ) > priceTolerancePercentage) ) {
                        // this stock was found to have at least one error
                        individualstockresult = false;
                        inaccurateDataList.add("Q4 high price: " + Q4HighPrice + " EuroInvestor Price: " + euroHighPrice + " on " + euroTimeStamp);
                    }

                    if ( ((Math.abs(EuroLowPrice - Q4LowPrice) / Q4LowPrice ) >  priceTolerancePercentage) ) {
                        // this stock was found to have at least one error
                        individualstockresult = false;
                        inaccurateDataList.add("Q4 low price: " + euroOpenPrice + " EuroInvestor Price: " + euroOpenPrice + " on " + euroTimeStamp);
                    }

                    if ( ((Math.abs(EuroClosePrice - Q4ClosePrice) / Q4ClosePrice ) > priceTolerancePercentage) ) {
                        // this stock was found to have at least one error
                        individualstockresult = false;
                        inaccurateDataList.add("Q4 close price: " + EuroClosePrice + " EuroInvestor Price: " + Q4ClosePrice + " on " + euroTimeStamp);
                    }
                }
            }

            if (!foundMatch) {
                // this security is missing data
                individualstockresult = false;
                missingStockDataList.add("Q4 is missing Stock Data on " + euroTimeStamp);
            }

            }
        }

    private JSONArray getIntradayArray(String host) {

        try {

            // Sample API Request - http://ir.euroinvestor.com{{url}}/ServiceEngine/api/json/reply/RequestIntradayData?apiversion=1&solutionID=3002&instrumentid=1001419&customerKey=GulfKeystone&numberOfDays=5
            String Q4RequestQuery = protocol + host + "/ServiceEngine/api/json/reply/RequestIntradayData?apiversion=1&solutionID=" + solutionID + "&instrumentid=" + instrumentID + "&customerKey=" + customerKey + "&numberOfDays=" + numberofDays;

            // recording request strings
            if (host.equals(Q4Host)){
                Q4Request = Q4RequestQuery;
            } else {
                euroInvestorRequest = Q4RequestQuery;
            }

            // System.out.println("Q4 query = " + Q4RequestQuery);

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
                individualstockresult = false;
            }

        } catch (Exception e) { }

        return null;
    }

    public boolean stockDataIsPerfect() {

        return individualstockresult;
    }

    public List<String> getInaccurateStockDataList() {

        return inaccurateDataList;
    }

    public List<String> getMissingDataList() {

        return missingStockDataList;
    }

    public List<String> getMiscellaneousErrorList() {

        // adding request failures
        miscellaneousErrorList.add("Q4 Request = <a href=\""+Q4Request+"\" target=\"_blank\">"+Q4Request+"</a>");
        miscellaneousErrorList.add("Euroinvestor Request =  <a href=\""+euroInvestorRequest+"\" target=\"_blank\">"+euroInvestorRequest+"</a>" );
        return miscellaneousErrorList;
    }
}

