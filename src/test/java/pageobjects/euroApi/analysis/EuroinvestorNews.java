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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by easong on 4/10/17.
 */
public class EuroinvestorNews {


    // assume data is correct. If the data is found to be incorrect, this boolean will be adjusted to false
    private boolean newsDataIsAccurate = true;

    private final JSONObject individualdata;
    private final HttpClient client;
    private String instrumentID;
    private String solutionID;
    private String customerKey;

    // Variables used to build requests
    private final String protocol = "http://";
    private final String Q4Host = "q4eurotestir.q4web.com";
    private final String euroInvestorHost = "ir.euroinvestor.com";
    private final boolean headlinesOnly = true;
    private final int maxRows = 20000;

    // Time tolerance variables
    private final long validationStartTime = 1491609600000L;  // this is for April 7th 2017
    private final long timeTolerance = 180000L; // this is for 3 minutes

    // error reporting variables
    private String Q4Request;
    private String euroInvestorRequest;
    private List<String> miscellaneousErrorList  = new ArrayList<>();
    private List<String> mismatchedNewsList = new ArrayList<>();
    private List<String> unsubscribedClientList = new ArrayList<>();


    public EuroinvestorNews(JSONObject globalindividualdata) throws IOException {

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

            JSONArray EuroInvestorData = getNewsArray(euroInvestorHost);

            // checking if Euroinvestor returned an array of data
            if (EuroInvestorData == null || EuroInvestorData.toString().equals("[]")) {
                // no data was found in the request, storing error in list
                unsubscribedClientList.add("Client is not subscribed to News" );
                // printing request
                unsubscribedClientList.add("Euroinvestor Request =  <a href=\""+euroInvestorRequest+"\" target=\"_blank\">"+euroInvestorRequest+"</a>" );
                return;
            }

            JSONArray Q4Data = getNewsArray(Q4Host);

            // checking if Q4 returned an array of data
            if (Q4Data == null || Q4Data.toString().equals("[]")) {
                // no data was found in the request, storing error in list
                miscellaneousErrorList.add("Q4 Returned no data" );
                newsDataIsAccurate = false;
                return;

            }

            //comparing data from Euroinvestor to Q4
            compareData(Q4Data, EuroInvestorData);

            //comparing data reverse from Q4 to Euroinvestor
            compareDataReverse(Q4Data, EuroInvestorData);

        } catch (Exception e ) {}

    }

    private void compareDataReverse(JSONArray q4Data, JSONArray euroInvestorData) {

        /*
        if (q4Data.length() != euroInvestorData.length()) {
            miscellaneousErrorList.add("Q4 returned " + q4Data.length() + " news items, EuroInvestor returned " + euroInvestorData.length());
        }
        */

        // parsing through both arrays at the same time
        for (int arrayCounter = 0; arrayCounter < q4Data.length(); arrayCounter++) {

            // boolean to track matching within the Q4Array
            boolean foundHeadlineMatch = false;
            boolean foundTimeStampMatch = false;

            // tracking timestamp difference
            long timedifference = 12345678910L;

            // collecting individual storyIDs from individual JSONObjects
            org.json.JSONObject Q4Quote = q4Data.getJSONObject(arrayCounter);
            String Q4Headline = (Q4Quote.get("headline").toString());
            String Q4TimeStamp = Q4Quote.get("timestamp").toString();

            boolean validDateRange = ValidateDateRange(Q4TimeStamp);

            if (validDateRange) {

                for (int euroInvestorArrayCounter = 0; euroInvestorArrayCounter < euroInvestorData.length(); euroInvestorArrayCounter++) {

                    // parsing through Q4Array trying to find the same storyID
                    org.json.JSONObject euroInvestorQuote = euroInvestorData.getJSONObject(euroInvestorArrayCounter);
                    String euroInvestorHeaderline = (Q4Quote.get("headline").toString());
                    String euroInvestorTimeStamp = (Q4Quote.get("timestamp").toString());

                    // comparing the headlines and ensuring their timestamp is no more than the tolerance apart
                    if (Q4Headline.equals(euroInvestorHeaderline)) {

                        // found a headline match
                        foundHeadlineMatch = true;
                        // recording smallest time difference
                        if (timedifference > Math.abs(toEpoch(Q4TimeStamp) - toEpoch(euroInvestorTimeStamp))) {
                            timedifference = Math.abs(toEpoch(Q4TimeStamp) - toEpoch(euroInvestorTimeStamp));
                        }

                        if (timedifference < timeTolerance) {
                            //both criteria matches
                            foundTimeStampMatch = true;

                            // ending loop
                            break;
                        }
                    }

                    if (!foundTimeStampMatch) {
                        // this security has out dated news data
                        newsDataIsAccurate = false;
                        mismatchedNewsList.add("Timestamps are " + timedifference / 60000 + " minutes apart for headline: " + Q4Quote.get("headline").toString() + " Link: " + "<a href=\"" + Q4Quote.get("htmlUrl").toString() + "\" target=\"_blank\">" + Q4Quote.get("htmlUrl").toString() + "</a>");
                    }

                    if (!foundHeadlineMatch) {
                        // this security is missing news data
                        newsDataIsAccurate = false;
                        mismatchedNewsList.add("Q4 has extra headline: " + Q4Quote.get("headline").toString() + " on " + Q4Quote.get("timestamp").toString() + " Link: " + "<a href=\"" + Q4Quote.get("htmlUrl").toString() + "\" target=\"_blank\">" + Q4Quote.get("htmlUrl").toString() + "</a>");
                    }

                }
            }
        }
    }

    private void compareData(JSONArray q4Data, JSONArray euroInvestorData) {

        /*
        if (q4Data.length() != euroInvestorData.length()) {
            miscellaneousErrorList.add("Q4 returned " + q4Data.length() + " news items, EuroInvestor returned " + euroInvestorData.length());
        }
        */

        // parsing through both arrays at the same time
        for (int arrayCounter = 0; arrayCounter < euroInvestorData.length(); arrayCounter++) {

            // boolean to track matching within the Q4Array
            boolean foundHeadlineMatch = false;
            boolean foundTimeStampMatch = false;

            // tracking timestamp difference
            long timedifference = 12345678910L;

            // collecting individual storyIDs from individual JSONObjects
            org.json.JSONObject EuroInvestorQuote = euroInvestorData.getJSONObject(arrayCounter);
            String euroInvestorHeadline = (EuroInvestorQuote.get("headline").toString());
            String euroInvestorDate = EuroInvestorQuote.get("timestamp").toString();

            boolean validDateRange = ValidateDateRange(euroInvestorDate);

            if (validDateRange) {

                System.out.println("checking for " + euroInvestorHeadline);

                for (int Q4ArrayCounter = 0; Q4ArrayCounter < q4Data.length(); Q4ArrayCounter++) {

                    // parsing through Q4Array trying to find the same storyID
                    org.json.JSONObject Q4Quote = q4Data.getJSONObject(Q4ArrayCounter);
                    String Q4Headline = Q4Quote.get("headline").toString();
                    String Q4TimeStamp = Q4Quote.get("timestamp").toString();

                    // comparing the headlines and ensuring their timestamp is no more than the tolerance apart
                    if (euroInvestorHeadline.equals(Q4Headline)) {

                        // found a headline match
                        foundHeadlineMatch = true;
                        // recording smallest time difference
                        if (timedifference > Math.abs(toEpoch(Q4TimeStamp) - toEpoch(euroInvestorDate))) {
                            timedifference = Math.abs(toEpoch(Q4TimeStamp) - toEpoch(euroInvestorDate));
                        }

                        if (timedifference < timeTolerance) {
                            //both criteria matches
                            foundTimeStampMatch = true;

                            // ending loop

                            break;
                        }
                    }

                }

                if (!foundTimeStampMatch) {
                    // this security has out dated news data
                    newsDataIsAccurate = false;
                    mismatchedNewsList.add("Timestamps are " + timedifference / 60000 + " minutes apart for headline: " + EuroInvestorQuote.get("headline").toString() + " Link: " + "<a href=\"" + EuroInvestorQuote.get("htmlUrl").toString() + "\" target=\"_blank\">" + EuroInvestorQuote.get("htmlUrl").toString() + "</a>");
                }

                if (!foundHeadlineMatch) {
                    // this security is missing news data
                    newsDataIsAccurate = false;
                    mismatchedNewsList.add("Q4 is missing headline: " + EuroInvestorQuote.get("headline").toString() + " on " + EuroInvestorQuote.get("timestamp").toString() + " Link: " + "<a href=\"" + EuroInvestorQuote.get("htmlUrl").toString() + "\" target=\"_blank\">" + EuroInvestorQuote.get("htmlUrl").toString() + "</a>");
                }
            }
        }
    }

    private void checkPDF(String q4PDFLink) {

        // changing euroinvestor domain to Q4 domain. this is temporary as it is a change that has not been implemented by DevOps. Euroinvestor's domain doesn't work because we've started using own our story IDs
        q4PDFLink = q4PDFLink.replaceAll("ir1.euroinvestor.com", "q4eurotestir.q4web.com");

        try {

            // sending request for PDF
            HttpGet get = new HttpGet(q4PDFLink);
            HttpResponse response = client.execute(get);

            // Checking resposne code
            if (response.getStatusLine().getStatusCode() != 200) {
                miscellaneousErrorList.add("Response code from Q4 PDF was " + response.getStatusLine().getStatusCode());
                miscellaneousErrorList.add("PDF Link: " + q4PDFLink);
                newsDataIsAccurate = false;
            }

        } catch (IOException e) {}
    }


    private boolean ValidateDateRange(String euroInvestorDate) {

        euroInvestorDate = euroInvestorDate.replace("0000Z","");

        // sample returned date format 2017-04-05T16:25:47.0000000Z
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS");

        // using epoch time to convert lengths
        long epoch = 0;

        try {
            Date date = dateFormat.parse(euroInvestorDate);
            epoch = date.getTime();

        } catch (Exception e) { System.out.println("could not parse " + euroInvestorDate );}

        // checking if date is after April 7th 2017 (the beginning of news in Q4 DB)
        return ( epoch > validationStartTime );
    }

    private long toEpoch(String Date) {

        Date = Date.replace("0000Z","");

        // sample returned date format 2017-04-05T16:25:47.0000000Z
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS");

        // using epoch time to convert lengths
        long epoch = 0;
        Date date = null;

        try {
            date = dateFormat.parse(Date);
            epoch = date.getTime();

        } catch (Exception e) { System.out.println("could not parse " + Date );}

        return epoch;
    }

    private JSONArray getNewsArray(String host) {

        try {

            // Sample API Request - http://ir.euroinvestor.com/ServiceEngine/api/json/reply/RequestNews?lcid=2057&maxRows=3&headlinesOnly=true&instrumentID=1000512&apiVersion=1&solutionID=2284&customerKey=scandinaviantobacco
            String Q4RequestQuery = protocol + host + "/ServiceEngine/api/json/reply/RequestNews?lcid=2057&maxRows=" + maxRows + "&headlinesOnly=" + headlinesOnly + "&instrumentID=" + instrumentID + "&apiVersion=1&solutionID=" + solutionID + "&customerKey=" + customerKey;

            // recording request strings
            if (host.equals(Q4Host)){
                Q4Request = Q4RequestQuery;
            } else {
                euroInvestorRequest = Q4RequestQuery;
            }

            //System.out.println("Q4 query = " + Q4RequestQuery);

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
                newsDataIsAccurate = false;
            }


        } catch (Exception e) {}

        return null;
    }

    public boolean isNewsDataIsAccurate() {
        return newsDataIsAccurate;
    }

    public List<String> getInaccurateNewsList() {
        return mismatchedNewsList;
    }

    public List<String> getUnsubscribedClientList() {

        return unsubscribedClientList;
    }

    public  List<String> getMiscellaneousErrorList() {

        // adding request failures
        miscellaneousErrorList.add("Q4 Request = <a href=\""+Q4Request+"\" target=\"_blank\">"+Q4Request+"</a>");
        miscellaneousErrorList.add("Euroinvestor Request =  <a href=\""+euroInvestorRequest+"\" target=\"_blank\">"+euroInvestorRequest+"</a>" );
        return miscellaneousErrorList;
    }
}
