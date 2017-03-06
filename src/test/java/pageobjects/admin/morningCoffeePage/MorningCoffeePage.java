package pageobjects.admin.morningCoffeePage;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.AbstractPageObject;
import sun.jvm.hotspot.oops.Mark;


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
    private final By addReportButton = By.xpath("//div[contains(@class,'action-buttons')]//button");

    private final By companySymbolTextField =By.xpath("//q4-morning-coffee-create//input");
    private final By reportCreationCancelButton = By.xpath("//q4-morning-coffee-create//button[contains(text(),'Cancel')]");
    private final By reportCreationCreateButton = By.xpath("//q4-morning-coffee-create//button[contains(text(),'Create')]");
    private final By ownerColumn = By.xpath("//th[contains(@class,'ui-sortable-column') and span[contains(text(),'Owner')]]");
    private final By dateColumn = By.xpath("//th[contains(@class,'ui-sortable-column') and span[contains(text(),'Date')]]");
    private final By reportTableRows = By.xpath("//tr[contains(@class, 'ui-datatable')]");
    private ArrayList<WebElement> tableRowsReports = new ArrayList<>();
    private String username="QA Test";


    //COMMENTARY TAB SELECTORS
    private final By marketSegment = By.xpath("//button[contains(text(),'Market')]");
    private final By sectorSegment = By.xpath("//button[contains(text(),'Sector')]");
    private final By categoryDropdown = By.xpath("//q4-morning-coffee-commentary-create/p-dropdown[1]/div"); //not the best selector
    private final By sectorDropdown = By.xpath("//p-dropdown[2]//label");
    private final By createCommentaryBox = By.className("ql-editor");
    private final By saveCommentaryButton = By.xpath("//q4-morning-coffee-commentary-create//button[contains(text(),'Save')]");
    private final By cancelCommentaryButton = By.xpath("//q4-morning-coffee-commentary-create//button[contains(text(),'Cancel')]");
    private final By marketTable = By.xpath("//div[contains(@class,'ui-datatable-tablewrapper')]");
    private final By sectorTable = By.xpath("//div[contains(@class,'ui-datatable-tablewrapper')]");

    private final By editCommentaryButton = By.xpath(".//button[contains(@class,'square-button')]");
    private final By saveEditedCommentaryButton = By.xpath("//q4-morning-coffee-commentary-edit//button[contains(text(),'Save')]");
    private final By cancelEditedCommentaryButton = By.xpath("//q4-morning-coffee-commentary-edit//button[contains(text(),'Cancel')]");


    private ArrayList<WebElement> marketRowData = new ArrayList<>();
    private ArrayList<WebElement> sectorRowData = new ArrayList<>();

    private String recentReportCompany = ""; //used to save info for the latest viewed report. We use this when confirming that a report has been deleted ( we no longer see this compant)
    private String recentReportDate = "";

    public MorningCoffeePage(WebDriver driver){super(driver);}




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

    public boolean recentReportExists(String symbol,Date date){
        if(findReport(symbol,date)!=null) {
            return true;
        }
        else
            return false;

    }

    public Date recentReportDate(String symbol,Date date){
        DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy, hh:mm:ss a");
        Date reportDate =new Date();
        WebElement report = findReport(symbol,date);
        try {
            System.out.print(report.findElement(By.xpath(".//td[6]")).getText()+" pre delete\n");
            reportDate = dateFormat.parse(report.findElement(By.xpath(".//td[6]")).getText());
            System.out.print(dateFormat.format(reportDate)+ " what it comes back as");
        }
        catch(Exception e){
            reportDate = null;
        }
        return reportDate;
    }



    public MorningCoffeePreview clickRecentReport(String symbol,Date date) {
         WebElement report = findReport(symbol,date);
        wait.until(ExpectedConditions.textToBePresentInElement(report,"Ready"));
        pause(500L);
        report.click();
        waitForLoadingScreen();
        return new MorningCoffeePreview(driver);
    }

    public boolean confirmReportDelete(String symbol,Date dateOfReport){
        DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy, h:mm:ss a");
        System.out.print(dateFormat.format(dateOfReport)+ " recent report");
        try {
            if(findReportWithDate(symbol,dateOfReport)==null){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            return false;
        }

    }

    private WebElement findReportWithDate(String symbol, Date currentDate){
        tableRowsReports = new ArrayList<>(readTableRows());
        DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy, hh:mm:ss a");
        if(tableRowsReports!=null){
            for(WebElement row : tableRowsReports) {
                if (row.getText().contains(symbol) && row.getText().contains(username) && row.getText().contains(dateFormat.format(currentDate))){
                    return row;
                }
            }
        }
        return null;
    }

    private WebElement findReport(String symbol, Date currentDate){
        tableRowsReports = new ArrayList<>(readTableRows());
        DateFormat dateFormat = new SimpleDateFormat("MMM d, YYYY");
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

    public MorningCoffeePage clickDateHeader(){
        wait.until(ExpectedConditions.elementToBeClickable(dateColumn));
        findElement(dateColumn).click();
        waitForLoadingScreen();
        return this;
    }

    public boolean isOwnerSortedAscending(){
        return isOwnerSorted(true);
    }

    public boolean isOwnerSortedDescending(){
        return isOwnerSorted(false);
    }

    private boolean isOwnerSorted(boolean ascendingSort){
        WebElement ownerHeader = findElement(ownerColumn);

         tableRowsReports = new ArrayList<>(readTableRows());
         ArrayList<WebElement> ownerNames= new ArrayList<>();

        for (WebElement row : tableRowsReports){
             ownerNames.add(row.findElement(By.xpath(".//td[2]")));
        }

        if(ascendingSort)
            return elementsAreAlphaUpSorted(ownerNames);
        else
            return elementsAreAlphaDownSorted(ownerNames);
        }

    public boolean isDateSortedAscending(){
      return isDateSorted(true);
    }

    public boolean isDateSortedDescending(){
        return isDateSorted(false);
    }

    private boolean isDateSorted(boolean ascendingSort){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy, hh:mm:ss");
        WebElement dateheader = findElement(dateColumn);
        tableRowsReports = new ArrayList<>(readTableRows());
        ArrayList<WebElement> ownerNames= new ArrayList<>();

        for (WebElement row : tableRowsReports){
            ownerNames.add(row.findElement(By.xpath(".//td[6]")));
        }
        if(ascendingSort)
            return elementsAreDateUpSorted(ownerNames,dateFormat);
        else
            return elementsAreDateDownSorted(ownerNames,dateFormat);

    }

    // COMMENTARY TAB
    public  MorningCoffeePage clickCommentaryTab(){
        wait.until(ExpectedConditions.elementToBeClickable(commentaryTab));
        findElement(commentaryTab).click();
        waitForLoadingScreen();
        return this;
    }

    public MorningCoffeePage clickMarketSegment(){
        findElement(marketSegment).click();
        return this;
    }

    public MorningCoffeePage clickSectorSegment(){
        findElement(sectorSegment).click();
        return this;
    }

    public MorningCoffeePage clickAddCommentarty(){
        wait.until(ExpectedConditions.presenceOfElementLocated(addReportButton));
        findVisibleElement(addReportButton).click();
        waitForLoadingScreen();
        return this;
    }

    public MorningCoffeePage createCommentary(Enum<?> commentaryType, String commentary){
        clickAddCommentarty();

        if(commentaryType.getClass().equals(Sector.class)){
            selectCatergory(Category.SECTOR);
            selectSector((Sector)commentaryType);
            enterInitialCommentary(commentary);
            findElement(saveCommentaryButton).click();
        }
        else{
           selectCatergory(Category.MARKET);
            selectMarket((Market)commentaryType);
            enterInitialCommentary(commentary);
            findElement(saveCommentaryButton).click();
        }
    return this;
    }

    public MorningCoffeePage clickAddReport(){
        findElement(addReportButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(companySymbolTextField));
        return this;
    }

    public MorningCoffeePage selectCatergory(Category category){
        findElement(categoryDropdown).click();
        findVisibleElement(By.className("ui-dropdown-item")).findElement(By.xpath("//span[contains(text(),'"+category.getCategory()+"')]")).click();
        return this;
    }
    public MorningCoffeePage selectSector(Sector sector){
        findElement(sectorDropdown).click();
        waitForLoadingScreen();
        clickElementLocation(By.xpath("//li[contains(@class,'ui-dropdown-item')][./span[contains(text(),'"+sector.getSector()+"')]]"));
        return this;
    }
    public MorningCoffeePage selectMarket(Market market){
        findElement(sectorDropdown).click();
        waitForLoadingScreen();
        clickElementLocation(By.xpath("//li[contains(@class,'ui-dropdown-item')][./span[contains(text(),'"+market.getMarket()+"')]]"));
        return this;
    }

    public MorningCoffeePage enterInitialCommentary(String commentary){
        findElement(createCommentaryBox).sendKeys(commentary);
        return this;
    }

    public MorningCoffeePage saveInitialCommentary(){
        wait.until(ExpectedConditions.elementToBeClickable(saveCommentaryButton));
        findElement(saveCommentaryButton).click();
        return this;
    }

    public MorningCoffeePage cancelInitialCommentary(){
        wait.until(ExpectedConditions.elementToBeClickable(cancelCommentaryButton));
        findElement(cancelCommentaryButton).click();
        return this;
    }

    private ArrayList<WebElement> retrieveMarketRowData(){
       ArrayList<WebElement> rowContents = new ArrayList<>(findVisibleElement(marketTable).findElements(By.xpath(".//tr[contains(@class,'ui-datatable')]")));
       waitForLoadingScreen();
       return rowContents;
    }

    private ArrayList<WebElement> retrieveSectorRowData(){
        ArrayList<WebElement> rowContents = new ArrayList<>(findVisibleElement(sectorTable).findElements(By.xpath(".//tr[contains(@class,'ui-datatable')]")));
        waitForLoadingScreen();
        return rowContents;
    }

    private WebElement returnMarketElement(Market market){
        marketRowData= retrieveMarketRowData();

        for(WebElement row: marketRowData){
            if(row.findElement(By.xpath(".//td[1]")).getText().equals(market.getMarket())){
                return row;
            }
        }
        return null;
    }

    private WebElement returnSectorElement(Sector sector){
        sectorRowData= retrieveSectorRowData();

        for(WebElement row: sectorRowData){
            if(row.findElement(By.xpath(".//td[1]")).getText().equals(sector.getSector())){
                return row;
            }
        }
        return null;
    }


   public  MorningCoffeePage editMarketCommentary(Market market,String comment){
        WebElement row = returnMarketElement(market);
        row.findElement(editCommentaryButton).click();
        findElement(createCommentaryBox).clear();
        findElement(createCommentaryBox).sendKeys(comment);
        return this;
   }

    public  MorningCoffeePage editSectorCommentary(Sector sector,String comment){
        WebElement row = returnSectorElement(sector);
        row.findElement(editCommentaryButton).click();
        findElement(createCommentaryBox).clear();
        findElement(createCommentaryBox).sendKeys(comment);
        return this;
    }

   public MorningCoffeePage saveEditedCommentary(){
       findElement(saveEditedCommentaryButton).click();
       return this;
   }

   public MorningCoffeePage cancelEditedCommentary(){
       findElement(cancelEditedCommentaryButton).click();
       return this;
   }

   public String returnMarketCommentary(Market market){
       waitForLoadingScreen();
       WebElement element =returnMarketElement(market);

       if(element!=null) {
           return element.findElement(By.xpath(".//td[2]")).getText();
       }
       else
           return null;
   }

    public String returnSectorCommentary(Sector sector){
       waitForLoadingScreen();
        WebElement element =returnSectorElement(sector);

        if(element!=null) {
            return element.findElement(By.xpath(".//td[2]")).getText();
        }
        else
            return null;
    }


    public void print(){
       System.out.print(returnSectorElement(Sector.ENERGY).getText());

    }

}
