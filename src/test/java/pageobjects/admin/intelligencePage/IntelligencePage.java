package pageobjects.admin.intelligencePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-11-09.
 */
public class IntelligencePage extends AbstractPageObject {
    private final By newReportButton = By.cssSelector(".page-header .action-buttons .add");
    private final By companySymbolField = By.cssSelector(".undefined");
    private final By createReportButton = By.cssSelector("button.button.button-yellow");
    private final By newReportInList = By.cssSelector("body > q4-app > div > div > q4-report > p-datatable > div > div > table > tbody > tr:nth-child(1)");
    private final By reportDropdown = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-dropdown .ui-inputtext");
    private final By weeklyTradeSummary = By.cssSelector(".ui-dropdown-open > div:nth-child(5) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(2)");
    private final By weeklyOptionsReport = By.cssSelector(".ui-dropdown-open > div:nth-child(5) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(3)");
    private final By salesEquityAndOptions = By.cssSelector(".ui-dropdown-open > div:nth-child(5) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(4)");
    private final By searchResult = By.cssSelector(".ui-autocomplete-list-item");


    public IntelligencePage(WebDriver driver) {
        super(driver);
    }

    public IntelligencePage createWeeklyTradeSummary(String symbol) {
        waitForLoadingScreen();
        findElement(newReportButton).click();
        findElement(reportDropdown).click();
        findElement(weeklyTradeSummary).click();
        pause(500L);
        findElement(companySymbolField).sendKeys(symbol);
        findElement(searchResult).click();
        findElement(createReportButton).click();

        return this;
    }

    public IntelligencePage createWeeklyOptionsAnalytics(String symbol) {
        waitForLoadingScreen();
        findElement(newReportButton).click();
        findElement(reportDropdown).click();
        findElement(weeklyOptionsReport).click();
        pause(500L);
        findElement(companySymbolField).sendKeys(symbol);
        findElement(searchResult).click();
        findElement(createReportButton).click();

        return this;
    }

    public IntelligencePage createSalesEquitAndOptions(String symbol) {
        waitForLoadingScreen();
        findElement(newReportButton).click();
        findElement(reportDropdown).click();
        findElement(salesEquityAndOptions).click();
        pause(500L);
        findElement(companySymbolField).sendKeys(symbol);
        findElement(searchResult).click();
        findElement(createReportButton).click();

        return this;
    }

    public String getNewReport() {
        waitForLoadingScreen();
        return findElement(newReportInList).getText();
    }
}
