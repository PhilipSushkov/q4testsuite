package pageobjects.admin.morningCoffeePage;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.AbstractPageObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private final By ownerColumn = By.xpath("//th[contains(@class,'ui-sortable-column') and span[contains(text(),'Owner')]]");
    private final By reportTableRows = By.xpath("//tr[contains(@class, 'ui-datatable')]");
    private ArrayList<WebElement> tableRowsReports = new ArrayList<>();
    private String username="QA Test";

    public MorningCoffeePage(WebDriver driver){super(driver);}

    public MorningCoffeePage clickAddReport(){
        findElement(addReportButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(companySymbolTextField));
        return this;
    }

    public MorningCoffeePage inputCompanySymbol(String symbol){
        findElement(companySymbolTextField).click();
        findElement(companySymbolTextField).sendKeys(symbol);
        findElement(By.xpath("//li[contains(@class,'ui-autocomplete-list-item') and .//div[contains(text(),'"+symbol+"')]]")).click(); //This is the autocomplete element. A report cannot be created until this is selected
        return this;
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

    private List<WebElement> readTableRows(){
        waitForLoadingScreen();
        List<WebElement> rowContents = findElements(reportTableRows);
        return rowContents;
    }

    public boolean recentReportExists(String symbol, Date currentDate){
        if(findReport(symbol,currentDate)!=null) {
            return true;
        }
        else
            return false;

    }

    public void clickRecentReport(String symbol, Date currentDate) {
         WebElement report = findReport(symbol,currentDate);
        wait.until(ExpectedConditions.textToBePresentInElement(report,"Ready"));
        pause(500L);
        report.click();

    }

    private WebElement findReport(String symbol, Date currentDate){
        tableRowsReports = new ArrayList<>(readTableRows());
        DateFormat dateFormat = new SimpleDateFormat("MMM DD, YYYY");
        System.out.print(dateFormat.format(currentDate).toString());
        if(tableRowsReports!=null){
            for(WebElement row : tableRowsReports) {
                if (row.getText().contains(symbol) && row.getText().contains(username) &&  row.getText().contains(dateFormat.format(currentDate).toString())){
                    return row;
                }
            }
        }
        return null;
    }

    public MorningCoffeePage clickOwnerHeader(){
        wait.until(ExpectedConditions.elementToBeClickable(ownerColumn));
        findElement(ownerColumn).click();
        waitForLoadingScreen();
        return this;
    }

    public boolean isOwnerSorted(){
        WebElement ownerHeader = findElement(ownerColumn);

         tableRowsReports = new ArrayList<>(readTableRows());
         ArrayList<WebElement> ownerNames= new ArrayList<>();

        for (WebElement row : tableRowsReports){
             ownerNames.add(row.findElement(By.xpath(".//td[2]")));
        }


        if(ownerHeader.findElement(By.className("fa-sort-asc"))!=null){
            return elementsAreAlphaUpSorted(ownerNames);
        }
        else if (ownerHeader.findElement(By.className("fa-sort-asc"))!=null){
            return elementsAreAlphaUpSorted(ownerNames);
        }
        else{
         return false;
        }
    }

}