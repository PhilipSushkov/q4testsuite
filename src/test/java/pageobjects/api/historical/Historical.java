package pageobjects.api.historical;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-03-08.
 */
public class Historical extends util.Functions {
    private static String sPathToFileAuth, sDataFileAuthJson, sPathToFileHist, sDataFileHistJson;
    private static JSONParser parser;
    private static HttpClient client;
    private static final String STAGING_ENV = "Staging_Env", SECURITIES = "Securities";

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
        JSONObject jsonEnvData = new JSONObject();
        JSONObject jsonEnv = new JSONObject();
        JSONObject jsonHistData = new JSONObject();
        JSONArray securityArray = new JSONArray();
        JSONObject jsonHist = new JSONObject();

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

        System.out.println(jsonEnv.get("access_token"));
        System.out.println(securityArray.toString());



        // {{url}}/api/stock/historical?appver={{appver}}&exchange={{Exchange}}&symbol={{Ticker}}


        return true;
    }

}
