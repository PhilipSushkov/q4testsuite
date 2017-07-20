package pageobjects.euroApi.analysis;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by philipsushkov on 2017-04-05.
 */

public class RequestSource {
    //private static HttpClient client;

    public RequestSource() {
        super();
    }

    public Boolean getRequestSource() throws IOException {
        int i=0;
        JSONParser parser = new JSONParser();
        JSONArray arr = new JSONArray();
        JSONArray arrSave = new JSONArray();
        String sPathToFile, sDataFile, sSaveFile, sHost, sSolutionID, sCustomerKey;

        sPathToFile = System.getProperty("user.dir") + "/src/test/java/pageobjects/euroApi/analysis/json/";
        sDataFile = "requestEuroSource.json";
        sSaveFile = "requestEuroSave.json";

        //client = HttpClientBuilder.create().build();

        try {
            FileReader readJsonFile = new FileReader(sPathToFile + sDataFile);
            arr = (JSONArray) parser.parse(readJsonFile);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClearJSONfile(sPathToFile + sSaveFile);

        //System.out.println(arr.toJSONString());

        sHost = "http://ir.euroinvestor.com";

        for (Iterator<JSONObject> iterator = arr.iterator(); iterator.hasNext(); arr.size()) {
            JSONObject obj = iterator.next();
            //System.out.println(obj.toJSONString());

            sSolutionID = obj.get("SolutionID").toString();
            sCustomerKey = obj.get("CustomerKey").toString();

            String urlQuery = sHost + "/ServiceEngine/api/json/reply/RequestStockDataBundle?apiversion=1&solutionID="+sSolutionID+"&customerKey="+sCustomerKey+"&instrumentTypes=Listing";

            HttpGet get = new HttpGet(urlQuery);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(get);

            //System.out.println(response.getStatusLine().getStatusCode()+": "+urlHistQuery);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
                //System.out.println(jsonResponse.toString());

                try {
                    FileReader readJsonFileSave = new FileReader(sPathToFile + sSaveFile);
                    arrSave = (JSONArray) parser.parse(readJsonFileSave);
                } catch (ParseException e) {
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }

                org.json.JSONArray arrDataItems = (org.json.JSONArray) jsonResponse.get("data");
                for (int j=0; j < arrDataItems.length(); j++) {

                    org.json.JSONObject objDataItem = (org.json.JSONObject) arrDataItems.get(j);

                    org.json.JSONObject objSave = new org.json.JSONObject();

                    try {
                        objSave.put("CustomerKey", sCustomerKey);
                        objSave.put("SolutionID", sSolutionID);
                        objSave.put("IRInstrumentID", objDataItem.get("instrumentID"));
                        objSave.put("name", objDataItem.get("name"));
                        objSave.put("symbol", objDataItem.get("symbol"));
                        objSave.put("currency", objDataItem.get("currency"));
                        objSave.put("exchangeName", objDataItem.get("exchangeName"));
                    } catch (JSONException e) {
                    }

                    arrSave.add(objSave);

                }

                try {
                    FileWriter writeFile = new FileWriter(sPathToFile + sSaveFile);
                    writeFile.write(arrSave.toString().replace("\\", ""));
                    writeFile.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                i++;
                System.out.println(obj.toJSONString());
                System.out.println("#"+i+" "+ response.getStatusLine().getStatusCode()+": "+urlQuery);
            }

        }

        return true;
    }


    // Clear JSON file
    public static void ClearJSONfile(String sPathToFile) {
        try {
            FileWriter fileClear = new FileWriter(sPathToFile);
            fileClear.write("");
            fileClear.flush();
            fileClear.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
