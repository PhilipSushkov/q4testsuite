package specs.euroApi.ImageComparison;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.euroApi.imageComparison.WidgetAppearance;
import specs.ApiAbstractSpec;
import util.Functions;
import util.LocalDriverManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-04-17.
 */

public class CheckWidgetAppearance extends ApiAbstractSpec {
    private static String sPathToFileWid, sDataFileWidOldJson, sDataFileWidNewJson;
    private static JSONParser parser;
    private static final int NUM_THREADS = 15, RESPONSE_200 = 200;
    private final String WIDGET_OLD_DATA="getWidgetOldData", WIDGET_NEW_DATA="getWidgetNewData";
    private final String SOLUTION_FOLDER = "Solution Folder", URL_EURO = "URL EURO", URL_Q4 = "URL Q4", IMG_SCALE="100%";
    private static ExtentReports extent;

    @BeforeTest
    public void setUp() throws IOException {
        sPathToFileWid = System.getProperty("user.dir") + propEuroAPI.getProperty("dataPath_Wid");
        sDataFileWidOldJson = propEuroAPI.getProperty("json_WidOldData");
        sDataFileWidNewJson = propEuroAPI.getProperty("json_WidNewData");

        parser = new JSONParser();
        extent = ExtentManager.GetExtent();
    }

    @Test(dataProvider=WIDGET_OLD_DATA, threadPoolSize=NUM_THREADS, priority=1, enabled=false)
    public void checkQ4WidOldAppearance(JSONObject data) throws IOException, InterruptedException {
        getWidgetComparison(data);
    }

    @Test(dataProvider=WIDGET_NEW_DATA, threadPoolSize=NUM_THREADS, priority=2, enabled=true)
    public void checkQ4WidNewAppearance(JSONObject data) throws IOException, InterruptedException {
        getWidgetComparison(data);
    }

    public void getWidgetComparison(JSONObject data) throws IOException, InterruptedException {
        String sSolutionFolder, sUrlEuro, sUrlQ4, sUrlEuroTitle, sUrlQ4Title, sShotName, sPageName, sPathScr;
        String SOLUTIONS = "Solutions";

        sSolutionFolder = data.get(SOLUTION_FOLDER).toString();
        sUrlEuro = data.get(URL_EURO).toString();
        sUrlQ4 = data.get(URL_Q4).toString();
        sShotName = new WidgetAppearance(LocalDriverManager.getDriver(), data).getShotName(sUrlQ4);
        sPageName = new WidgetAppearance(LocalDriverManager.getDriver(), data).getPageName(sUrlQ4, SOLUTIONS);
        sPathScr = propEuroAPI.getProperty("dataPath_Scr");

        ExtentTest test = extent.createTest("Widget Comparison results for "+sSolutionFolder+": "+sShotName);


        if (Functions.GetResponseCode(sUrlEuro) == RESPONSE_200) {
            test.log(Status.PASS, "Response Code of URL EURO <a href=\""+sUrlEuro+"\" target=\"_blank\">"+sUrlEuro+"</a> is 200");
        } else {
            test.log(Status.FAIL, "Response Code of URL EURO <a href=\""+sUrlEuro+"\" target=\"_blank\">"+sUrlEuro+"</a> is not 200");
        }

        sUrlEuroTitle = new WidgetAppearance(LocalDriverManager.getDriver(), data).getTitle(sUrlEuro);


        if (Functions.GetResponseCode(sUrlQ4) == RESPONSE_200) {
            test.log(Status.PASS, "Response Code of URL Q4  <a href=\""+sUrlQ4+"\" target=\"_blank\">"+sUrlQ4+"</a> is 200");
        } else {
            test.log(Status.FAIL, "Response Code of URL Q4  <a href=\""+sUrlQ4+"\" target=\"_blank\">"+sUrlQ4+"</a> is not 200");
        }

        sUrlQ4Title = new WidgetAppearance(LocalDriverManager.getDriver(), data).getTitle(sUrlQ4);


        if (sUrlQ4Title.equals(sUrlEuroTitle)) {
            test.log(Status.PASS, "Q4 Widget Page Title <a href=\""+sUrlQ4+"\" target=\"_blank\">"+sUrlQ4+"</a> is correct");
        } else {
            test.log(Status.FAIL, "Q4 Widget Page Title <a href=\""+sUrlQ4+"\" target=\"_blank\">"+sUrlQ4+"</a> " +
                    "isn't the same as expected: <a href=\""+sUrlEuro+"\" target=\"_blank\">"+sUrlEuro+"</a>");
        }


        long curThread = new WidgetAppearance(LocalDriverManager.getDriver(), data).getQ4WidgetAppearance();
        if (curThread==0) {
            test.log(Status.PASS, "Q4 Widget Appearance <a href=\""+sUrlQ4+"\" target=\"_blank\">"+sUrlQ4+"</a> is correct");
        } else {
            test.log(Status.FAIL, "Q4 Widget Appearance <a href=\""+sUrlQ4+"\" target=\"_blank\">"+sUrlQ4+"</a> " +
                    "isn't the same as expected: <a href=\""+sUrlEuro+"\" target=\"_blank\">"+sUrlEuro+"</a>"+
                    "<p>EI-Q4 Difference: <br><img src=\""+sPathScr+"Q4/"+sPageName+"/diff_"+sShotName+"_"+curThread+".png\" alt=\"Diff "+sPageName+": " +sShotName+"\" width=\""+IMG_SCALE+"\" height=\""+IMG_SCALE+"\"></img></p>"+
                    "<p>EI Widget: <br><img src=\""+sPathScr+"Euro/"+sPageName+"/"+sShotName+"_"+curThread+".png\" alt=\"Euro "+sPageName+": "+sShotName+"\" width=\""+IMG_SCALE+"\" height=\""+IMG_SCALE+"\"></img></p>"+
                    "<p>Q4 Widget: <br><img src=\""+sPathScr+"Q4/"+sPageName+"/"+sShotName+"_"+curThread+".png\" alt=\"Q4 "+sPageName+": "+sShotName+"\" width=\""+IMG_SCALE+"\" height=\""+IMG_SCALE+"\"></img></p>");
        }
    }

    @DataProvider(name=WIDGET_OLD_DATA, parallel = true)
    public Object[][] getWidgetOldData(Method method) {

        try {
            System.out.println(sPathToFileWid + sDataFileWidOldJson);
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileWid + sDataFileWidOldJson));

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

    @DataProvider(name=WIDGET_NEW_DATA, parallel = true)
    public Object[][] getWidgetNewData(Method method) {

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileWid + sDataFileWidNewJson));

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

    @AfterTest(alwaysRun=true)
    public void tearDown() {
        //phDriver.quit();
        extent.flush();
    }

}
