package pageobjects.euroApi.analysis;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by easong on 4/28/17.
 */
public class EuroinvestorDelayed {

    // assume data is correct. If the data is found to be incorrect, this boolean will be adjusted to false
    private boolean DelayedDataIsAccurate = true;

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
    private final double timeToleranceInMilliseconds = 300000; // 5 minutes

    // error reporting variables
    private String Q4Request;
    private String euroInvestorRequest;
    private List<String> miscellaneousErrorList = new ArrayList<>();
    private List<String> inaccurateDataList = new ArrayList<>();

    public EuroinvestorDelayed (org.json.simple.JSONObject globalindividualdata) throws IOException {

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
            JSONObject Q4Data = getDelayedQuote(Q4Host);
            JSONObject EuroInvestorData = getDelayedQuote(euroInvestorHost);

            // checking if Euroinvestor returned an array of data
            if (EuroInvestorData == null) {
                // no data was found in the request, storing error in list
                miscellaneousErrorList.add("Euroinvestor Returned no data");
                DelayedDataIsAccurate = false;
                return;
            }

            // checking if Q4 returned an array of data
            if (Q4Data == null) {
                // no data was found in the request, storing error in list
                miscellaneousErrorList.add("Q4 Returned no data");
                DelayedDataIsAccurate = false;
                return;
            }

            //comparing data between the two arrays
            compareData(Q4Data, EuroInvestorData);

        } catch (Exception e) {
        }
    }

    private void compareData(JSONObject q4Data, JSONObject euroInvestorData) {

        // getting euro data
        long euroInvestorTradeTimeStamp = toEpoch(euroInvestorData.get("tradeTimestamp").toString());
        double euroInvestorLast = Double.parseDouble(euroInvestorData.get("last").toString());

        // getting Q4 data
        long Q4TradeTimeStamp = toEpoch(q4Data.get("tradeTimestamp").toString());
        double Q4Last = Double.parseDouble(q4Data.get("last").toString());

        // checking time tolerance. End test if time tolerance is broken since the data won't matter
        if ( Math.abs(Q4TradeTimeStamp - euroInvestorTradeTimeStamp) > timeToleranceInMilliseconds )
        {
            DelayedDataIsAccurate = false;
            miscellaneousErrorList.add("TimeStamp: Q4 time is " + q4Data.get("tradeTimestamp").toString() + " EuroInvestor time is " + euroInvestorData.get("tradeTimestamp").toString() );
            return;
        }

        // checking tolerance
        if ( ( Math.abs(Q4Last - euroInvestorLast) / Q4Last ) > priceTolerancePercentage )
        {
            DelayedDataIsAccurate = false;
            inaccurateDataList.add("Inaccuracy: Q4 price is " + Q4Last + " EuroInvestor Price is " + euroInvestorLast );
        }
    }

    private JSONObject getDelayedQuote(String host) {

        try {

            // Sample API Request - ir.euroinvestor.com/ServiceEngine/api/json/reply/RequestStockData?apiversion=1&solutionid=2240&customerkey=BeHeardGroupPlc&instrumentid=1000336&format=json
            String Q4RequestQuery = protocol + host + "/ServiceEngine/api/RequestStockData?apiversion=1&customerkey=" + customerKey + "&instrumentid=" + instrumentID + "&solutionid=" + solutionID + "&format=json";

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

                // creating a JSONObject within the data object
                org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
                JSONObject jsonObject = jsonResponse.optJSONObject("data");

                return jsonObject;

            } else {
                // initial request to database was not successful, storing error in list
                miscellaneousErrorList.add("Response code from " + host + " was " + response.getStatusLine().getStatusCode());
                miscellaneousErrorList.add("Failed link: " + Q4RequestQuery);
                DelayedDataIsAccurate = false;
            }

        } catch (Exception e) {
        }

        return null;
    }

    private long toEpoch(String Date) {

        Date = Date.replace("0000","");

        // sample returned date format 2017-04-05T16:25:47.000
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

    public boolean isDelayedDataIsAccurate() {

        return DelayedDataIsAccurate;
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
