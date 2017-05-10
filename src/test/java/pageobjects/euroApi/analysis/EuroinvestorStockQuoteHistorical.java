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
 * Created by easong on 4/4/17.
 */
public class EuroinvestorStockQuoteHistorical {

    private double Q4Price = 0;
    private String Q4Date;
    private String EuroInvestorDate;
    private double EuroInvestorPrice;
    private String solutionID;
    private String yearRange = "40";
    private String customerKey;
    private boolean individualstockresult = true;

    private boolean result = false;
    private boolean dataexists = true;
    private HttpClient client;
    private final String Q4Host = "q4eurotestir.q4web.com", EuroInvestorHost = "ir.euroinvestor.com" , PROTOCOL = "http://";
    private org.json.JSONArray securityArray = new org.json.JSONArray();

    private HttpResponse Q4response;
    private HttpResponse EuroInvestorResponse;
    private JSONObject individualdata;

    // error reporting variables
    private String Q4Request;
    private String euroInvestorRequest;
    private List<String> zeroDataList = new ArrayList<>();
    private List<String> inaccurateDataList = new ArrayList<>();
    private List<String> missingDataList = new ArrayList<>();
    private List<String> miscellaneousErrorList = new ArrayList<>();


    public EuroinvestorStockQuoteHistorical(JSONObject globalindividualdata) throws IOException {

        individualdata = new JSONObject(globalindividualdata);

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
        client = HttpClientBuilder.create().build();
    }

    public void dataValidation() {

        try {
            // this boolean keep track of the data health for individual stocks
            individualstockresult = true;

            customerKey = individualdata.get("CustomerKey").toString();
            solutionID = individualdata.get("SolutionID").toString();

            // Sending the same request to both databases to obtain what should be 2 identical JSONArrays of Data
            JSONArray Q4Data = getStockQuoteArray(Q4Host);
            JSONArray EuroInvestorData = getStockQuoteArray(EuroInvestorHost);

            // checking if Q4 returned an array of data
            if (Q4Data == null) {
                miscellaneousErrorList.add("Q4 Returned no data");
                // no data was found in the request, storing error in list
                individualstockresult = false;
                return;
            }

            // checking if Euroinvestor returned an array of data
            if (EuroInvestorData == null) {
                miscellaneousErrorList.add("Euroinvestor Returned no data");
                // no data was found in the request, storing error in list
                individualstockresult = false;
                return;
            }

            //comparing data between the two arrays
            compareData(Q4Data, EuroInvestorData);

        } catch (Exception e) {
        }
    }

    private JSONArray getStockQuoteArray( String host) {

    try {

        // API Request format: {{url}}/ServiceEngine/api/json/reply/RequestClosePriceBundle_OHLC?apiVersion=1&solutionID={{solutionID}}&customerKey={{customerkey}}&numberOfYears={{#ofyears}}instrumentTypes=Listing
        String Q4RequestQuery = PROTOCOL + host + "/ServiceEngine/api/json/reply/RequestClosePriceBundle_OHLC?apiVersion=1&solutionID=" + solutionID + "&customerKey=" + customerKey + "&numberOfYears="+ yearRange + "&instrumentTypes=Listing";

        // recording request strings
        if (host.equals(Q4Host)){
            Q4Request = Q4RequestQuery;
        } else {
            euroInvestorRequest = Q4RequestQuery;
        }

        // System.out.println("Q4 query = " + Q4RequestQuery);


        HttpGet get = new HttpGet(Q4RequestQuery);
        Q4response = client.execute(get);

        if (Q4response.getStatusLine().getStatusCode() == 200) {

            HttpEntity entity = Q4response.getEntity();

            String responseBody = EntityUtils.toString(entity);
            org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
            JSONArray jsonArray = jsonResponse.optJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i ++)
            {
                org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Creating array of stock data where each day is the next index
                JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                return jsonArray1;
            }

        } else {

            // initial request to database was not successful, storing error in list
            miscellaneousErrorList.add("Response code from " + host + " was not 200, it was " + Q4response.getStatusLine().getStatusCode());
            miscellaneousErrorList.add("Failed link: " + Q4RequestQuery);

            individualstockresult = false;
        }

    } catch (Exception e) {

    }
        return null;
}

    private boolean compareData(JSONArray Q4Array, JSONArray EuroInvestorArray) {

        if (EuroInvestorArray.length() != Q4Array.length()) {
            miscellaneousErrorList.add("Q4 returned " + Q4Array.length() + " Stock Quotes, EuroInvestor returned " + EuroInvestorArray.length());
        }

        // parsing through euroInvestor array
        for (int arraycounter = 0; arraycounter < EuroInvestorArray.length(); arraycounter++) {

            // boolean to track matching within the Q4Array
            boolean foundMatch = false;

            org.json.JSONObject EuroInvestorQuote = EuroInvestorArray.getJSONObject(arraycounter);
            EuroInvestorPrice = Double.parseDouble(EuroInvestorQuote.get("closePrice").toString());
            EuroInvestorDate = EuroInvestorQuote.get("date").toString();

            for (int Q4ArrayCounter = 0; Q4ArrayCounter < Q4Array.length(); Q4ArrayCounter++) {

            org.json.JSONObject Q4Quote = Q4Array.getJSONObject(Q4ArrayCounter);
            Q4Price = Double.parseDouble(Q4Quote.get("closePrice").toString());
            Q4Date = Q4Quote.get("date").toString();
                // comparing the dates
                if (Q4Date.equals(EuroInvestorDate)) {
                    // declaring that a match was found
                    foundMatch = true;
                    // margin of acceptable error between the 2 stock prices
                    result = (Math.abs(Q4Price - EuroInvestorPrice) < 0.01);

                    if (!result) {
                        // this stock was found to have at least one error
                        individualstockresult = false;
                        inaccurateDataList.add("Q4 price: " + Q4Price + " EuroInvestor Price: " + EuroInvestorPrice + " on " + EuroInvestorDate);

                        // ending loop
                        break;
                    }
                }
            }

            if (!foundMatch) {
                // this security is missing news data
                individualstockresult = false;
                missingDataList.add("Q4 missing quote on: " + EuroInvestorDate + " Euroinvestor Price is " + EuroInvestorPrice );
            }
        }

        return result;
    }

    public boolean stockDataIsPerfect() {

        return individualstockresult;
    }

    public List<String> getZeroDataList() {

        return zeroDataList;
    }

    public List<String> getInaccurateStockDataList() {

        return inaccurateDataList;
    }

    public List<String> getMissingDataList() {

        return missingDataList;
    }

    public List<String> getMiscellaneousErrorList() {

        // adding request failures
        miscellaneousErrorList.add("Q4 Request = <a href=\""+Q4Request+"\" target=\"_blank\">"+Q4Request+"</a>");
        miscellaneousErrorList.add("Euroinvestor Request =  <a href=\""+euroInvestorRequest+"\" target=\"_blank\">"+euroInvestorRequest+"</a>" );
        return miscellaneousErrorList;
    }
}
