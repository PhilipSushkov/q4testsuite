package pageobjects.admin.morningCoffeePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by noelc on 2017-01-09.
 */
public class MorningCoffeePage extends AbstractPageObject {

    private final By commentaryTab = By.xpath("//span[contains(text(),'Commentary')]");
    private final By reportsTab = By.xpath("//span[contains(text(),'Commentary')]");
    private final By addReportButton = By.xpath("//div[contains(@class,'banner')]//button[contains(@class, 'add')]");
    private final By addMarketCommentary = By.xpath("//div[contains(@class,'section-header') and .//h2[contains(text(),'Markets')]]//button[contains(@class, 'add') and contains(@class,'square-button')]");
    private final By addSectorCommentary = By.xpath("//div[contains(@class,'section-header') and .//h2[contains(text(),'Sectors')]]//button[contains(@class, 'add') and contains(@class,'square-button')]");
    private final By companySymbolTextField =By.xpath("//q4-morning-coffee-create//input");
    private final By reportCreationCancelButton = By.xpath("//q4-morning-coffee-create//button[contains(text(),'Cancel')]");
    private final By reportCreationCreateButton = By.xpath("//q4-morning-coffee-create//button[contains(text(),'Create')]");

    public MorningCoffeePage(WebDriver driver){super(driver);}

    public MorningCoffeePage clickAddReport(){
        findElement(addReportButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(companySymbolTextField));
        return this;
    }

    public void inputCompanySymbol(String symbol){
        findElement(companySymbolTextField).click();
        findElement(companySymbolTextField).sendKeys(symbol);
    }

    public MorningCoffeePage clickCreateReport(){
        findElement(reportCreationCreateButton).click();
        waitForLoadingScreen();
        return this;
    }

    public MorningCoffeePage clickCancelReport(){
        findElement(reportCreationCancelButton).click();
        waitForLoadingScreen();
        return this;
    }


}