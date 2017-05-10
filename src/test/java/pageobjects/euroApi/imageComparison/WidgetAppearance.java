package pageobjects.euroApi.imageComparison;

import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import util.Functions;

import java.io.IOException;

import static specs.ApiAbstractSpec.propEuroAPI;

/**
 * Created by philipsushkov on 2017-04-17.
 */

public class WidgetAppearance {
    private WebDriver phDriver;
    private JSONObject data;
    private static final String sSlash = "/";
    private static final long DEFAULT_PAUSE = 2000;
    private final String URL_EURO = "URL EURO", URL_Q4 = "URL Q4", SOLUTIONS = "Solutions";
    private By copyrightTd;

    public WidgetAppearance(WebDriver phDriver, JSONObject data) {
        this.phDriver = phDriver;
        this.data = data;
        copyrightTd = By.xpath(propEuroAPI.getProperty("td_Copyright"));
    }

    public long getQ4WidgetAppearance() throws IOException, InterruptedException {
        long result=0;
        String sShotName, sPageName, urlEuro, urlQ4, sPath, pathEuro, pathQ4, pathDiff;

        long curThread = Thread.currentThread().getId();
        sPath = System.getProperty("user.dir") + "/" +propEuroAPI.getProperty("dataPath_Scr");


        // --------------------------------------
        // Euro Widgets
        urlEuro = data.get(URL_EURO).toString();
        phDriver.get(urlEuro);
        Thread.sleep(DEFAULT_PAUSE);

        sShotName = getShotName(urlEuro);
        sPageName = getPageName(urlEuro, SOLUTIONS);

        //System.out.println(sPageName+": "+sShotName);

        hideElement(copyrightTd);

        pathEuro = sPath + "Euro/"+sPageName + "/" + sShotName+"_"+curThread+".png";
        Functions.takeScreenshot(phDriver, sPath, sShotName, "Euro/"+sPageName, curThread);
        // --------------------------------------


        // --------------------------------------
        // Q4 Widgets
        urlQ4 = data.get(URL_Q4).toString();
        phDriver.get(urlQ4);
        Thread.sleep(DEFAULT_PAUSE);

        sShotName = getShotName(urlQ4);
        sPageName = getPageName(urlQ4, SOLUTIONS);

        hideElement(copyrightTd);

        pathQ4 = sPath + "Q4/"+sPageName + "/" + sShotName+"_"+curThread+".png";
        Functions.takeScreenshot(phDriver, sPath, sShotName, "Q4/"+sPageName, curThread);
        // --------------------------------------


        pathDiff = System.getProperty("user.dir")+"/"+propEuroAPI.getProperty("dataPath_Scr")+"Q4/"+sPageName+"/"+"diff_"+sShotName+"_"+curThread+".png";

        if (!Functions.compareImages(pathEuro, pathQ4, pathDiff)) {
            result = curThread;
        }

        return result;
    }

    public String getPageName(String url, String sCond) {
        String sPageName;
        String[] asNames = url.split(sSlash);

        if (asNames[3].equals(sCond)) {
            sPageName = asNames[4];
        } else {
            sPageName = asNames[5];
        }
        return sPageName;
    }

    public String getShotName(String url) {
        String[] asNames = url.split(sSlash);
        return asNames[asNames.length-1];
    }

    public void hideElement(By byElement) {
        try {
            WebElement eCopyrightCur = phDriver.findElement(byElement);
            Functions.hideElement(eCopyrightCur, phDriver);
        } catch (NoSuchElementException e) {
            try {
                phDriver.switchTo().frame(0);
                WebElement eCopyrightCur = phDriver.findElement(byElement);
                Functions.hideElement(eCopyrightCur, phDriver);
            } catch (NoSuchFrameException e1) {
            } catch (NoSuchElementException e2) {
            }
        }
    }

    public String getTitle(String sUrl) throws InterruptedException {
        phDriver.get(sUrl);
        Thread.sleep(DEFAULT_PAUSE);
        return phDriver.getTitle();
    }

}
