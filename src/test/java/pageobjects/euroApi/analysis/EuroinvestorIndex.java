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
 * Created by easong on 4/17/17.
 */
public class EuroinvestorIndex {

    // assume data is correct. If the data is found to be incorrect, this boolean will be adjusted to false
    private boolean indexDataIsCorrect = true;

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

    // error reporting variables
    private String Q4Request;
    private String euroInvestorRequest;
    private List<String> miscellaneousErrorList = new ArrayList<>();
    private List<String> missingArrayList = new ArrayList<>();
    private List<String> missingQuoteList = new ArrayList<>();
    private List<String> inaccurateDataList = new ArrayList<>();
    private String numberOfYears = "10";

    public EuroinvestorIndex(JSONObject globalindividualdata) throws IOException {

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
            JSONArray Q4Data = getIndexArray(Q4Host);
            JSONArray EuroInvestorData = getIndexArray(euroInvestorHost);

            // checking if Q4 returned an array of data
            if (Q4Data == null) {
                // no data was found in the request, storing error in list
                miscellaneousErrorList.add("Q4 Returned no data");
                indexDataIsCorrect = false;
                return;
            }

            // checking if Euroinvestor returned an array of data
            if (EuroInvestorData == null) {
                // no data was found in the request, storing error in list
                miscellaneousErrorList.add("Euroinvestor Returned no data");
                indexDataIsCorrect = false;
                return;
            }
            //comparing data between the two arrays
            compareData(Q4Data, EuroInvestorData);

        } catch (Exception e) {
        }
    }

    private JSONArray getIndexArray(String host) {

        try {

            // Sample API Request - http://ir.euroinvestor.com/ServiceEngine/api/json/reply/RequestClosePriceBundle_C?apiVersion=1&lcid=2057&customerKey=tdc&solutionID=2444&numberOfYears=1&instrumentTypes=Peer&instrumentTypes=Index
            String Q4RequestQuery = protocol + host + "/ServiceEngine/api/json/reply/RequestClosePriceBundle_C?apiVersion=1&lcid=2057&customerKey=" + customerKey + "&instrumentid=" + instrumentID + "&solutionid=" + solutionID + "&numberOfYears=" + numberOfYears + "&instrumentTypes=Peer&instrumentTypes=Index";

            // recording request strings
            if (host.equals(Q4Host)) {
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
                indexDataIsCorrect = false;
            }

        } catch (Exception e) {
        }

        return null;
    }

    private void compareData(JSONArray q4Data, JSONArray euroInvestorData) {

        // parsing through both arrays at the same time
        for (int EuroArrayCounter = 0; EuroArrayCounter < euroInvestorData.length(); EuroArrayCounter++) {

            // boolean to track matching within the Q4Array
            boolean indexMatchFound = false;

            // collecting information of index from the euroinvestor Database
            org.json.JSONObject euroInvestorIndex = euroInvestorData.getJSONObject(EuroArrayCounter);
            String euroInvestorIndexName = euroInvestorIndex.get("name").toString();
            // collecting array of the euroinvestor index
            JSONArray euroInvestorIndexQuoteArray = euroInvestorIndex.getJSONArray("data");
            // initializing array of the Q4 Index
            JSONArray Q4IndexQuoteArray = null;

            // recording the name of the index
            String Q4IndexName = null;

            // Finding matching index from the Q4 Database
            for (int Q4ArrayCounter = 0; Q4ArrayCounter < q4Data.length(); Q4ArrayCounter++) {
                org.json.JSONObject Q4Index = q4Data.getJSONObject(Q4ArrayCounter);
                Q4IndexName = Q4Index.get("name").toString();

                // seeing if there is a matching array
                if (Q4IndexName.equals(euroInvestorIndexName)) {
                    // found a matching index
                    indexMatchFound = true;
                    // grabbing the data array for the matching quote
                    Q4IndexQuoteArray = Q4Index.getJSONArray("data");
                    // ending loop
                    break;
                }
            }

            if (!indexMatchFound) {
                // recording missing data
                indexDataIsCorrect = false;
                missingArrayList.add("Q4 missing Index: " + euroInvestorIndexName);
            } else {
                // comparing individual quotes within these data arrays
                compareQuotes(Q4IndexQuoteArray, euroInvestorIndexQuoteArray, Q4IndexName);
            }

        }
    }

    private void compareQuotes(JSONArray q4IndexQuoteArray, JSONArray euroInvestorIndexQuoteArray, String Q4IndexName) {

        for (int euroCounter = 0; euroCounter < euroInvestorIndexQuoteArray.length(); euroCounter++) {

            // looking for a match in dates
            boolean matchFound = false;
            // getting first quote object
            org.json.JSONObject euroInvestorQuote = euroInvestorIndexQuoteArray.getJSONObject(euroCounter);
            String euroInvestorDate = euroInvestorQuote.get("date").toString();
            double euroInvestorClosePrice = Double.parseDouble(euroInvestorQuote.get("closePrice").toString());

            for (int q4Counter = 0; q4Counter < q4IndexQuoteArray.length(); q4Counter++) {

                org.json.JSONObject Q4Quote = q4IndexQuoteArray.getJSONObject(q4Counter);
                String Q4QuoteDate = Q4Quote.get("date").toString();
                double Q4QuoteDateClosePrice = Double.parseDouble(Q4Quote.get("closePrice").toString());

                if (euroInvestorDate.equals(Q4QuoteDate)) {
                    // match has been found
                    matchFound = true;
                    // comparing prices
                    if ((Math.abs(euroInvestorClosePrice - Q4QuoteDateClosePrice) > 0.5)) {
                        indexDataIsCorrect = false;
                        // recording inaccurate data
                        inaccurateDataList.add("Q4 price: " + Q4QuoteDateClosePrice + " EuroInvestor Price: " + euroInvestorClosePrice + " on " + euroInvestorDate);
                    }
                }
            }

            if (!matchFound) {
                indexDataIsCorrect = false;
                // recording missing quote
                missingQuoteList.add("Q4 missing quote on " + euroInvestorDate + " EuroInvestor Price: " + euroInvestorClosePrice + " for index " + Q4IndexName);
            }
        }
    }

    public boolean IndexDataIsPerfect() {

        return indexDataIsCorrect;
    }

    public List<String> getInaccurateIndexDataList() {

        return inaccurateDataList;
    }

    public List<String> getMissingQuoteList() {

        return missingQuoteList;
    }

    public List<String> getMissingArrayList() {

        return missingArrayList;
    }

    public List<String> getMiscellaneousErrorList() {

        // adding request failures
        miscellaneousErrorList.add("Q4 Request = <a href=\""+Q4Request+"\" target=\"_blank\">"+Q4Request+"</a>");
        miscellaneousErrorList.add("Euroinvestor Request =  <a href=\""+euroInvestorRequest+"\" target=\"_blank\">"+euroInvestorRequest+"</a>" );
        return miscellaneousErrorList;
    }
}

