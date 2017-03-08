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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class Auth extends util.Functions {
    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;
    private static final String STAGING_ENV = "Staging_Env";
    private static final String PATHTO_API_PROP = "api/ApiMap.properties";
    public static Properties propAPI;

    public Auth() throws IOException {
        parser = new JSONParser();
        setupPropUI();

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);

        sPathToFile = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Auth");
        sDataFileJson = propAPI.getProperty("jsonData_Auth");
    }

    public boolean getAccessToken() throws IOException {
        String access_token = null;
        HttpClient client;
        JSONObject jsonData = new JSONObject();
        JSONObject jsonEnv = new JSONObject();

        try {
            FileReader readFile = new FileReader(sPathToFile + sDataFileJson);
            jsonData = (JSONObject) parser.parse(readFile);
            jsonEnv = (JSONObject) jsonData.get(STAGING_ENV);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(jsonEnv.toString());

        String urlAuth = jsonEnv.get("url_auth").toString();
        String appver = jsonEnv.get("app_ver").toString();

        String urlAuthQuery = urlAuth+"?"+appver;

        //System.out.println(urlAuthQuery);

        HttpPost post = new HttpPost(urlAuthQuery);
        post.setHeader("User-Agent", jsonEnv.get("user_agent").toString());

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("id", jsonEnv.get("id").toString()));
        urlParameters.add(new BasicNameValuePair("password", jsonEnv.get("password").toString()));
        urlParameters.add(new BasicNameValuePair("product", jsonEnv.get("product").toString()));
        urlParameters.add(new BasicNameValuePair("user", jsonEnv.get("user").toString()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(post);

        //System.out.println("Response Code: " + response.getStatusLine().getStatusCode());

        if(response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                try {
                    JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
                    JSONObject token = (JSONObject) jsonResponse.get("token");
                    access_token = (String) token.get("access_token");
                    //System.out.println(access_token);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            jsonEnv.put("access_token", access_token);

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sDataFileJson);
                writeFile.write(jsonData.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return access_token != null;
    }

    public static void setupPropUI() throws IOException {
        propAPI = ConnectToPropUI(PATHTO_API_PROP);
    }

}
