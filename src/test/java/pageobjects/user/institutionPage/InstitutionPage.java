package pageobjects.user.institutionPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.Page;

import java.util.List;

/**
 * Created by patrickp on 2016-08-08.
 */
public class InstitutionPage extends Page {

    private final By institutionName  = By.className("page-title");
    private final By institutionIcon = By.className("institution");
    private final By targetIcon = By.className("q4i-savedtargets-2pt");
    private final By activistIcon = By.className("q4i-activist-2pt");
    private final By QrIcon = By.cssSelector(".rating .val");

    public InstitutionPage(WebDriver driver) {
        super(driver);
    }


    public String getInstitutionName() {
        waitForLoadingScreen();
        waitForAnyElementToAppear(institutionName);
        return findVisibleElement(institutionName).getText();
    }

    public String getURL(){
        waitForElementToAppear(institutionIcon);
        return driver.getCurrentUrl();
    }

    public boolean isSavedTarget(){
        waitForElement(QrIcon);
        return findVisibleElements(targetIcon).size() > 0;
    }

    public boolean isActivist(){
        waitForElement(activistIcon);
        return findVisibleElements(activistIcon).size() > 0;
    }
}
