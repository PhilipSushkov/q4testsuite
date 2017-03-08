package pageobjects.api.login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpEntity;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class Auth extends util.Functions {
    private String access_token = null;
    private HttpClient client = null;
    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;
    private static final String PATHTO_API_PROP = "api/ApiMap.properties";
    public static Properties propAPI;

    public Auth() throws IOException {
        parser = new JSONParser();
        setupPropUI();

        sPathToFile = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Auth");
        sDataFileJson = propAPI.getProperty("jsonData_Auth");
    }

    public boolean getAccessToken() {


        /*
        String urlAuth = "https://staging.q4touch.com/api/auth";
        String appver = "appver=1.5";
        String urlAuthQuery = urlAuth+"?"+appver;

        HttpPost post = new HttpPost(urlAuthQuery);
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("id", "UserLogin-1"));
        urlParameters.add(new BasicNameValuePair("password", "qwerty@01"));
        urlParameters.add(new BasicNameValuePair("product", "desktop"));
        urlParameters.add(new BasicNameValuePair("user", "pylypsushkov@gmail.com"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        System.out.println("Response Code: "
                + response.getStatusLine().getStatusCode());

        //JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        if(response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            //System.out.println("Entity:" + entity);
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                System.out.println("finalResult" + responseBody.toString());

                jsonObject = new JSONObject(responseBody.toString());

                Boolean success = (Boolean) jsonObject.get("success");
                System.out.println(success);

                JSONObject token = (JSONObject) jsonObject.get("token");
                access_token = (String) token.get("access_token");
                System.out.println(access_token);
            }

        }

        Assert.assertNotNull(access_token);

        */

        return true;
    }

    public static void setupPropUI() throws IOException {
        propAPI = ConnectToPropUI(PATHTO_API_PROP);
    }
}
