package specs.api.historical;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.api.historical.Historical;
import pageobjects.api.historical.HistoricalStockQuote;
import pageobjects.api.login.Auth;
import specs.ApiAbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class CheckHistorical extends ApiAbstractSpec {

    private static Historical historical;
    private static Auth auth;
    private static String sPathToFileHist, sDataFileHistJson;
    private static JSONParser parser;
    private final String STOCKDATA = "getData";
    private static final String DEVELOP_ENV = "Develop_Env";
    private static final String PREPROD_ENV = "Preprod_Env";

    @BeforeTest
    public void setUp() throws IOException {
        auth = new Auth();

        // gets access token for the desired environment
        Assert.assertTrue(new Auth().getAccessToken(PREPROD_ENV), "Access Token didn't receive");
        historical = new Historical();

        sPathToFileHist = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Hist");
        sDataFileHistJson = propAPI.getProperty("jsonData_Hist");
        parser = new JSONParser();
    }

    @Test(dataProvider = STOCKDATA, threadPoolSize = 5)
    public void CheckQ4DesktopAuth(JSONObject data) throws IOException {

        JSONObject individualdata = new JSONObject(data);

        // 2nd parameter specifies which environment to run in
        HistoricalStockQuote historicalStockQuote = new HistoricalStockQuote(individualdata, PREPROD_ENV );

        // begin data validation process
        historicalStockQuote.dataValidation();
        Assert.assertTrue(historicalStockQuote.stockDataIsAccurate(),"Stock data is inaccurate for " + data.get("symbol").toString());
    }

    @DataProvider (parallel = true)
    public Object[][] getData() {

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileHist + sDataFileHistJson));
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < stockDataArray.size(); i++) {
                    zoom.add(stockDataArray.get(i));
            }

            Object[][] stockData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                stockData[i][0] = zoom.get(i);
            }

            return stockData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
