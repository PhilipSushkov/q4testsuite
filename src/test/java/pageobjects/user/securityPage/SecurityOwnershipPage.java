package pageobjects.user.securityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.institutionPage.InstitutionPage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class SecurityOwnershipPage extends AbstractPageObject {
    private final By tabTitle = By.cssSelector(".company-header .menu-button .x-button-label");
    private final By dateOption = By.cssSelector(".company-ownership-inner .range-tabs-inner .x-button-no-icon");
    private final By asOfDate = By.cssSelector(".company-ownership-inner .disclaimer span");
    private final By topBuyersNumbers = By.cssSelector(".top-buyers-list .inst-value:not(:empty)");
    private final By topSellersNumbers = By.cssSelector(".top-sellers-list .inst-value:not(:empty)");
    private final By topBuyersAndSellers = By.cssSelector(".list-item .inst-name");
    private final By lastTopSeller = By.cssSelector(".company-ownership-inner > div > div > div:not(.x-hidden-display) .top-sellers-list .x-dataview-item:nth-child(5) .list-item");

    // holders table
    private final By activistFilter = By.cssSelector(".ownership-report-holders .toggle-button");
    private final By activistFilterOn = By.cssSelector(".ownership-report-holders .toggle-button .x-toggle-on");
    private final By activistFilterOff = By.cssSelector(".ownership-report-holders .toggle-button .x-toggle-off");
    private final By allTypesFilter = By.xpath("//span[contains(text(),'All')]");
    private final By institutionsFilter = By.cssSelector("span.q4i-institution-2pt");
    private final By insidersFilter = By.cssSelector("span.q4i-insider-2pt");
    private final By fundsFilter = By.cssSelector("span.q4i-fund-2pt");
    private final By allBuyersSellersFilter = By.cssSelector(".ownership-report-main-content .range-tabs .x-button:first-child");
    private final By buyersFilter = By.cssSelector(".ownership-report-main-content .range-tabs .x-button:nth-child(2)");
    private final By sellersFilter = By.cssSelector(".ownership-report-main-content .range-tabs .x-button:nth-child(3)");
    private final By holderTableHeaderName = By.cssSelector(".x-grid-column:first-child");
    private final By holderTableHeaderPOS = By.cssSelector(".x-grid-column:nth-child(2)");
    private final By holderTableHeader1QChg = By.xpath("//*[@id=\"ext-column-87\"]");
    private final By holderTableHeaderMktVal = By.cssSelector(".x-grid-column:nth-child(4)");
    private final By holderTableHeaderMktValChg = By.cssSelector(".x-grid-column:nth-child(5)");
    private final By holderTableHeaderPercOS = By.cssSelector(".x-grid-column:nth-child(6)");
    private final By holderTableHeaderPercPort = By.cssSelector(".x-grid-column:nth-child(7)");
    private final By holderTableHeaderStyle = By.cssSelector(".x-grid-column:nth-child(8)");
    private final By holderTableHeaderTurnover = By.cssSelector(".x-grid-column:nth-child(9)");
    private final By holderTableHeaderAUM = By.cssSelector(".x-grid-column:nth-child(10)");
    private final By holderTableHeaderAsOf = By.cssSelector(".x-grid-column:nth-child(11)");
    private final By holderTableHeaderQR = By.cssSelector(".x-grid-column:nth-child(12)");
    private final By holderTableRow = By.cssSelector(".x-grid-row:not([style*='-10000px'])");
    private final By alternateHolderTableRow = By.cssSelector(".top-holders-list-institutions .x-dataview-item"); // exists instead of above when using Buyers or Sellers filter
    private final By holderTableName = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:first-child");
    private final By holderTablePOS = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(2)");
    private final By holderTable1QChg = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(3) span");
    private final By holderTableMktVal = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(4)");
    private final By holderTableMktValChg = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(5) span");
    private final By holderTablePercOS = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(6)");
    private final By holderTablePercPort = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(7)");
    private final By holderTableStyle = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(8)");
    private final By holderTableTurnover = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(9)");
    private final By holderTableAUM = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(10)");
    private final By holderTableAsOf = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(11)");
    private final By holderTableQR = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(12)");
    private final By holderTable1W = By.cssSelector(".x-dataview-item .view-list-item:nth-child(4)"); // only exists when using Buyers or Sellers filter
    private final By showMoreButton = By.className("q4i-arrow-down-2pt");
    private final By activistIcon = By.cssSelector(".icon.activists");
    private final By institutionIcon = By.cssSelector("i.q4i-institution-2pt");

    // institutional ownership section
    private final By styleBarsBlue = By.cssSelector(".style + .ownership-report-institutional-charts rect[fill='#297ac5']");
    private final By styleBarsRed = By.cssSelector(".style + .ownership-report-institutional-charts rect[fill='#ec6a4c']");
    private final By styleBarsOrange = By.cssSelector(".style + .ownership-report-institutional-charts rect[fill='#fc7e26']");
    private final By styleBarsYellow = By.cssSelector(".style + .ownership-report-institutional-charts rect[fill='#f1af0f']");
    private final By turnoverBarsBlue = By.cssSelector(".turnover + .ownership-report-institutional-charts rect[fill='#297ac5']");
    private final By turnoverBarsRed = By.cssSelector(".turnover + .ownership-report-institutional-charts rect[fill='#ec6a4c']");
    private final By turnoverBarsOrange = By.cssSelector(".turnover + .ownership-report-institutional-charts rect[fill='#fc7e26']");
    private final By turnoverBarsYellow = By.cssSelector(".turnover + .ownership-report-institutional-charts rect[fill='#f1af0f']");
    private final By institutionalPercentages = By.cssSelector(".highcharts-data-labels g");

    // trend analysis section
    private final By trendAnalysisChartBody = By.cssSelector(".area-chart .highcharts-series path:first-child");
    private final By trendAnalysisHoverText = By.cssSelector(".ownership-report-trend-analysis-content .highcharts-tooltip");

    // institutional holder analysis section
    private final By holderBreakdownValues = By.cssSelector(".analysis-breakdown-list .value");
    private final By holderTypeValues = By.cssSelector(".analysis-investor-type-list .value");
    private final By holderTypeOther = By.cssSelector(".analysis-investor-type-list .other");
    private final By holderStyleValues = By.cssSelector(".analysis-pie-bundles .q4-pie-bundle:nth-child(1) text");
    private final By holderStyleOther = By.cssSelector(".analysis-pie-bundles .q4-pie-bundle:nth-child(1) .other");
    private final By holderTurnoverValues = By.cssSelector(".analysis-pie-bundles .q4-pie-bundle:nth-child(2) text");
    private final By holderTurnoverOther = By.cssSelector(".analysis-pie-bundles .q4-pie-bundle:nth-child(2) .other");
    private final By holderOtherDropdown = By.className("q4-list-modal-inner");
    private final By holderOtherValues = By.cssSelector(".q4-list-modal-inner .value"); //values contained within whatever other dropdown is open (returns no elements when closed)

    Actions actions = new Actions(driver);
    private final DateTimeFormatter shortDate = DateTimeFormatter.ofPattern("MM/dd/yy");
    private final DateTimeFormatter longDate = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    private final LocalDate today = LocalDate.now();
    private final By historicalTab = By.cssSelector("#ext-tab-9");
    private final By currentTab = By.cssSelector("#ext-tab-5");
    private final By holdingsSearchField = By.cssSelector(".ownership-top-holders.q4-tab .search-field .x-field-input .x-input-el");

    //different types of Holder Searches
    private final By historicalInstitutionsHolderSearchResult = By.cssSelector("#ext-top-holders-historical-institutions-1");
    private final By currentInsidersHolderSearchResult = By.cssSelector("#ext-ownership-top-holders-current-1");
    private final By historicalFundsHolderSearchResults = By.cssSelector("#ext-top-holders-historical-fund-1");
    private final By holderSearchResult = By.cssSelector("#ext-top-holders-historical-institutions-1");
    private final By holderSearchResulttwo = By.cssSelector(".top-holders-list.fund .details .holder-info");
    private final By FundsETFsTab = By.cssSelector("#ext-tab-8");
    private final By InstitutionTab = By.cssSelector("#ext-tab-2");
    private final By InstitutionSearchResult = By.cssSelector(".top-holders-list.institution .details .holder-info .name");
    private final By InsidersTab = By.cssSelector("#ext-tab-7");
    private final By InsiderSearchResult = By.cssSelector(".top-holders-list.insider .details .holder-info .name");
    private final By DefaultInsiderResult = By.cssSelector("#ext-element-1965");
    private final By DefaultHolderResult = By.cssSelector("#ext-element-5048");

    public SecurityOwnershipPage(WebDriver driver) {
        super(driver);
    }

    public String getOwnershipTabTitle() {
        waitForLoadingScreen();
        return findElement(tabTitle).getText();
    }

    //// UNLESS OTHERWISE INDICATED, ALL METHODS ON THIS PAGE ARE DESIGNED TO OCCUR WHEN THE SURVEILLANCE TAB IS SELECTED. \\\\
    //      (They may or may not work while one of the date options is selected.)

    // index goes from 0 to 4, with 0 being end of last quarter
    public void clickLocation(By selector){
        Actions builder =  new Actions(driver);
        builder.moveToElement(findVisibleElement(selector)).click().build().perform();
    }

    public SecurityOwnershipPage selectDate(int index){
        waitForElement(dateOption);
        findElements(dateOption).get(index).click();
        pause(500);
        wait.until(ExpectedConditions.elementToBeClickable(lastTopSeller));
        return this;
    }

    // checks that the date tabs indicate the end dates of the last four quarters
    public boolean dateOptionsAreValid(){
        waitForElement(dateOption);
        LocalDate endOfLastQuarter;
        if (today.getMonthValue() > 3){
            endOfLastQuarter = today.withMonth((today.getMonthValue()-1)/3*3).with(TemporalAdjusters.lastDayOfMonth());
        }
        else {
            endOfLastQuarter = today.minusYears(1).with(TemporalAdjusters.lastDayOfYear());;
        }

        if (!LocalDate.parse(findElements(dateOption).get(0).getText(), shortDate).equals(endOfLastQuarter)){
            System.out.println("First date option is incorrect.\n\tExpected: "+endOfLastQuarter.format(shortDate)
                    +"\n\tDisplayed: "+findElements(dateOption).get(0).getText());
            return false;
        }
        if (!LocalDate.parse(findElements(dateOption).get(1).getText(), shortDate).equals(endOfLastQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth()))){
            System.out.println("Second date option is incorrect.\n\tExpected: "+endOfLastQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth()).format(shortDate)
                    +"\n\tDisplayed: "+findElements(dateOption).get(1).getText());
            return false;
        }
        if (!LocalDate.parse(findElements(dateOption).get(2).getText(), shortDate).equals(endOfLastQuarter.minusMonths(6).with(TemporalAdjusters.lastDayOfMonth()))){
            System.out.println("Third date option is incorrect.\n\tExpected: "+endOfLastQuarter.minusMonths(6).with(TemporalAdjusters.lastDayOfMonth()).format(shortDate)
                    +"\n\tDisplayed: "+findElements(dateOption).get(2).getText());
            return false;
        }
        if (!LocalDate.parse(findElements(dateOption).get(3).getText(), shortDate).equals(endOfLastQuarter.minusMonths(9).with(TemporalAdjusters.lastDayOfMonth()))){
            System.out.println("Fourth date option is incorrect.\n\tExpected: "+endOfLastQuarter.minusMonths(9).with(TemporalAdjusters.lastDayOfMonth()).format(shortDate)
                    +"\n\tDisplayed: "+findElements(dateOption).get(3).getText());
            return false;
        }

        return true;
    }

    // checks that the "As of" text is correct when each date option is selected
    public boolean asOfDateIsValid(){
        waitForElement(asOfDate);
        LocalDate lastFriday = today.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        LocalDate endOfQuarter;
        if (today.getMonthValue() > 3){
            endOfQuarter = today.withMonth((today.getMonthValue()-1)/3*3).with(TemporalAdjusters.lastDayOfMonth());
        }
        else {
            endOfQuarter = today.minusYears(1).with(TemporalAdjusters.lastDayOfYear());
        }
        LocalDate startOfQuarter = endOfQuarter.minusMonths(2).with(TemporalAdjusters.firstDayOfMonth());

        // with Surveillance tab selected, as of date should be last friday
        if (!LocalDate.parse(findElement(asOfDate).getText(), longDate).equals(lastFriday)){
            System.out.println("As of date while in surveillance mode is incorrect.\n\tExpected: "+lastFriday.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }

        // select first date tab and check that as of date is start - end of last quarter
        selectDate(0);
        pause(500);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting first date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }

        // select second date tab and check that as of date is start - end of quarter before that
        selectDate(1);
        pause(500);
        endOfQuarter = endOfQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());
        startOfQuarter = startOfQuarter.minusMonths(3);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting second date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }

        // select third date tab and check that as of date is start - end of quarter before that
        selectDate(2);
        pause(500);
        endOfQuarter = endOfQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());
        startOfQuarter = startOfQuarter.minusMonths(3);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting third date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }

        // select fourth date tab and check that as of date is start - end of quarter before that
        selectDate(3);
        pause(500);
        endOfQuarter = endOfQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());
        startOfQuarter = startOfQuarter.minusMonths(3);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting third date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }

        return true;
    }

    // checks that entries on top buyers list have positive change numbers
    public boolean topBuyersListIsPositive(){
        waitForElement(topBuyersNumbers);
        return elementsAreAllPositive(findVisibleElements(topBuyersNumbers));
    }

    // checks that entries on top sellers list have negative change numbers
    public boolean topSellersListIsNegative(){
        waitForElement(topSellersNumbers);
        return elementsAreAllNegative(findVisibleElements(topSellersNumbers));
    }

    // checks that entries on top buyers list have change numbers in descending order
    public boolean topBuyersListIsDescending(){
        waitForElement(topBuyersNumbers);
        return elementsAreNumDownSorted(findVisibleElements(topBuyersNumbers));
    }

    // checks that entries on top sellers list have change numbers in ascending order
    public boolean topSellersListIsAscending(){
        waitForElement(topSellersNumbers);
        return elementsAreNumUpSorted(findVisibleElements(topSellersNumbers));
    }

    // checks that no institution appears in both the top buyers and the top sellers list (or appears twice in either list)
    public boolean topBuyersAndSellersAreUnique(){
        waitForElement(topBuyersAndSellers);
        return elementsDoNotContainDuplicates(findVisibleElements(topBuyersAndSellers));
    }

    //// HOLDERS TABLE METHODS \\\\

    // this method does not work when Buyers or Sellers filter is selected
    public int getNumOfHoldersDisplayed(){
        waitForElement(holderTableName);
        return findVisibleElements(holderTableName).size();
    }

    // use this method when Buyers or Sellers filter is selected
    public int getNumOfHoldersDisplayedAlternate(){
        try {
            waitForElement(alternateHolderTableRow);
        }catch (TimeoutException e){ //will timeout if no entries are displayed or if buyers/sellers filter is not actually selected
            if (doesElementExist(holderTableName)){ //in case these entries become formatted like how the holders table is otherwise
                return findVisibleElements(holderTableName).size();
            }
            return 0;
        }
        return findVisibleElements(alternateHolderTableRow).size();
    }

    public SecurityOwnershipPage showMoreHolders(){
        waitForLoadingScreen();
        waitForElement(showMoreButton);
        int numHolders = getNumOfHoldersDisplayed();
        findVisibleElement(showMoreButton).click();
        for (int i=0; i<100; i++){
            if (getNumOfHoldersDisplayed() > numHolders){
                return this;
            }
            pause(100);
        }
        return this;
    }

    public boolean canSortByName(){
        waitForLoadingScreen();
        boolean isSorted = true;
        long length = 1000L;
        // sorting by name ascending
        findVisibleElement(holderTableHeaderName).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaUpSortedIgnoreCase(findElements(holderTableName))){
            System.out.println("Holders are not sorted by name ascending.");
            isSorted = false;
        }

        // sorting by name descending
        findVisibleElement(holderTableHeaderName).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaDownSorted(findElements(holderTableName))){
            System.out.println("Holders are not sorted by name descending.");
            isSorted = false;
        }
        return isSorted;
    }



    public boolean canSortByPOS(){
        waitForLoadingScreen();
        boolean isSorted = true;
        clickLocation(holderTableHeaderPOS);
        //findVisibleElement(holderTableHeaderPOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS ascending.");
            isSorted = false;
        }

        // sorting by POS descending
        findVisibleElement(holderTableHeaderPOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS descending.");
            isSorted = false;
        }

        return isSorted;
    }

    public boolean canSortBy1Q(){
        waitForLoadingScreen();
        boolean isSorted =true;
        clickLocation(holderTableHeader1QChg);
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change ascending.");
            isSorted = false;
        }

        // sorting by 1Q change descending
        clickLocation(holderTableHeader1QChg);
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortByMarketValue(){
        waitForLoadingScreen();
        boolean isSorted =true;
        findVisibleElement(holderTableHeaderMktVal).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value ascending.");
            isSorted = false;
        }

        // sorting by market value descending
        findVisibleElement(holderTableHeaderMktVal).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value descending.");
            isSorted = false;
        }
        return isSorted;
    }


    public boolean canSortByMarketValueChange(){
        waitForLoadingScreen();
        boolean isSorted= true;

        findVisibleElement(holderTableHeaderMktValChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change ascending.");
            isSorted = false;
        }

        // sorting by market value change descending
        findVisibleElement(holderTableHeaderMktValChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortByOS(){
        waitForLoadingScreen();
        boolean isSorted = true;
        findVisibleElement(holderTableHeaderPercOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS ascending.");
            isSorted = false;
        }

        // sorting by %OS descending
        findVisibleElement(holderTableHeaderPercOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS descending.");
            isSorted = false;
        }

        return isSorted;

    }

    public boolean canSortByPort(){
        waitForLoadingScreen();
        boolean isSorted=true;
        clickLocation(holderTableHeaderPercPort);
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port ascending.");
            isSorted = false;
        }

        // sorting by %Port descending
        clickLocation(holderTableHeaderPercPort);
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortByStyle(){
        waitForLoadingScreen();
        boolean isSorted =true;

        findVisibleElement(holderTableHeaderStyle).click();
        waitForLoadingScreen();;
        if (!elementsAreAlphaUpSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style ascending.");
            isSorted = false;
        }

        // sorting by style descending
        findVisibleElement(holderTableHeaderStyle).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaDownSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSoryByDate(){
        waitForLoadingScreen();
        boolean isSorted = true;
        findVisibleElement(holderTableHeaderAsOf).click();
        waitForLoadingScreen();
        if (!elementsAreDateUpSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date ascending.");
            isSorted = false;
        }

        // sorting by as of date descending
        findVisibleElement(holderTableHeaderAsOf).click();
        waitForLoadingScreen();
        if (!elementsAreDateDownSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortByQR() {
        boolean isSorted = true;// sorting by QR ascending
        waitForLoadingScreen();
        findVisibleElement(holderTableHeaderQR).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableQR))) {
            System.out.println("Holders are not sorted by QR ascending.");
            isSorted = false;
        }

        // sorting by QR descending
        findVisibleElement(holderTableHeaderQR).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableQR))) {
            System.out.println("Holders are not sorted by QR descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortHoldersTable(){
        boolean isSorted = true;
        long length = 1000L;
        // sorting by name ascending
        findVisibleElement(holderTableHeaderName).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaUpSortedIgnoreCase(findElements(holderTableName))){
            System.out.println("Holders are not sorted by name ascending.");
            isSorted = false;
        }

        // sorting by name descending
        findVisibleElement(holderTableHeaderName).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaDownSorted(findElements(holderTableName))){
            System.out.println("Holders are not sorted by name descending.");
            isSorted = false;
        }

        // sorting by POS ascending
        findVisibleElement(holderTableHeaderPOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS ascending.");
            isSorted = false;
        }

        // sorting by POS descending
        findVisibleElement(holderTableHeaderPOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS descending.");
            isSorted = false;
        }

        // sorting by 1Q change ascending
        findVisibleElement(holderTableHeader1QChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change ascending.");
            isSorted = false;
        }

        // sorting by 1Q change descending
        findVisibleElement(holderTableHeader1QChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change descending.");
            isSorted = false;
        }

        // sorting by market value ascending
        findVisibleElement(holderTableHeaderMktVal).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value ascending.");
            isSorted = false;
        }

        // sorting by market value descending
        findVisibleElement(holderTableHeaderMktVal).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value descending.");
            isSorted = false;
        }

        // sorting by market value change ascending
        findVisibleElement(holderTableHeaderMktValChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change ascending.");
            isSorted = false;
        }

        // sorting by market value change descending
        findVisibleElement(holderTableHeaderMktValChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change descending.");
            isSorted = false;
        }

        // sorting by %OS ascending
        findVisibleElement(holderTableHeaderPercOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS ascending.");
            isSorted = false;
        }

        // sorting by %OS descending
        findVisibleElement(holderTableHeaderPercOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS descending.");
            isSorted = false;
        }

        // sorting by %Port ascending
        findVisibleElement(holderTableHeaderPercPort).click();
        pause(length);
        if (!elementsAreNumUpSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port ascending.");
            isSorted = false;
        }

        // sorting by %Port descending
        findVisibleElement(holderTableHeaderPercPort).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port descending.");
            isSorted = false;
        }

        // sorting by style ascending
        findVisibleElement(holderTableHeaderStyle).click();
        waitForLoadingScreen();;
        if (!elementsAreAlphaUpSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style ascending.");
            isSorted = false;
        }

        // sorting by style descending
        findVisibleElement(holderTableHeaderStyle).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaDownSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style descending.");
            isSorted = false;
        }
/* No Longer sorting these because of performance issues
        // sorting by turnover ascending
        findVisibleElement(holderTableHeaderTurnover).click();
        pause(length);
        if (!elementsAreTurnoverUpSorted(findElements(holderTableTurnover))){
            System.out.println("Holders are not sorted by turnover ascending.");
            isSorted = false;
        }

        // sorting by turnover descending
        findVisibleElement(holderTableHeaderTurnover).click();
        pause(length);
        if (!elementsAreTurnoverDownSorted(findElements(holderTableTurnover))){
            System.out.println("Holders are not sorted by turnover descending.");
            isSorted = false;
        }

        // sorting by AUM ascending
        findVisibleElement(holderTableHeaderAUM).click();
        pause(length);
        if (!elementsAreNumUpSorted(findElements(holderTableAUM))){
            System.out.println("Holders are not sorted by AUM ascending.");
            isSorted = false;
        }

        // sorting by AUM descending
        findVisibleElement(holderTableHeaderAUM).click();
        pause(length);
        if (!elementsAreNumDownSorted(findElements(holderTableAUM))){
            System.out.println("Holders are not sorted by AUM descending.");
            isSorted = false;
        }
        */


        // sorting by as of date ascending
        findVisibleElement(holderTableHeaderAsOf).click();
        waitForLoadingScreen();
        if (!elementsAreDateUpSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date ascending.");
            isSorted = false;
        }

        // sorting by as of date descending
        findVisibleElement(holderTableHeaderAsOf).click();
        waitForLoadingScreen();
        if (!elementsAreDateDownSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date descending.");
            isSorted = false;
        }

        // sorting by QR ascending
        findVisibleElement(holderTableHeaderQR).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableQR))){
            System.out.println("Holders are not sorted by QR ascending.");
            isSorted = false;
        }

        // sorting by QR descending
        findVisibleElement(holderTableHeaderQR).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableQR))){
            System.out.println("Holders are not sorted by QR descending.");
            isSorted = false;
        }

        return isSorted;
    }

    // Checks that all values in the 1Q CHG and MKT VAL CHG columns are green if positive, red if negative, or dark grey if zero
    public boolean holderTableChangeValueColouringIsCorrect(){
        boolean correct = true;
        List<WebElement> values = findVisibleElements(holderTable1QChg);
        values.addAll(findVisibleElements(holderTableMktValChg));

        for (WebElement value : values){
            if (Double.parseDouble(value.getText().replace(",", "")) > 0){
                if (!value.getCssValue("color").equals("rgba(26, 188, 156, 1)")){
                    System.out.println("Positive change value of "+value.getText()+" is not green.\n\tExpected: rgba(26, 188, 156, 1)\n\tActual: "+value.getCssValue("color"));
                    correct = false;
                }
            }
            else if (Double.parseDouble(value.getText().replace(",", "")) < 0){
                if (!value.getCssValue("color").equals("rgba(236, 106, 76, 1)")){
                    System.out.println("Negative change value of "+value.getText()+" is not red.\n\tExpected: rgba(236, 106, 76, 1)\n\tActual: "+value.getCssValue("color"));
                    correct = false;
                }
            }
            else {
                if (!value.getCssValue("color").equals("rgba(84, 91, 98, 1)")){
                    System.out.println("Zero change value of "+value.getText()+" is not dark grey.\n\tExpected: rgba(84, 91, 98, 1)\n\tActual: "+value.getCssValue("color"));
                    correct = false;
                }
            }
        }

        return correct;
    }

    public String[] getHolderNames(){
        waitForElement(holderTableName);
        List<WebElement> elements = findVisibleElements(holderTableName);
        String[] names = new String[elements.size()];
        for (int i=0; i<elements.size(); i++){
            names[i] = elements.get(i).getText();
        }
        return names;
    }

    public InstitutionPage goToInstitutionalHolder(int index){
        waitForElement(holderTableName);
        findVisibleElements(holderTableName).get(index).click();
        return new InstitutionPage(driver);
    }

    public SecurityOwnershipPage showOnlyActivists(){
        waitForElement(activistFilter);
        if (doesElementExist(activistFilterOff)){ //does nothing if filter is already selected
            int numHolders = getNumOfHoldersDisplayed();
            findElement(activistFilter).click();
            for (int i=0; i<50; i++){ //waits up to 5 seconds for the number of holders displayed to change
                if (getNumOfHoldersDisplayed() != numHolders){
                    return this;
                }
                pause(100);
            }
        }
        waitForLoadingScreen();
        return this;
    }

    public SecurityOwnershipPage doNotShowOnlyActivists(){
        waitForElement(activistFilter);
        if (doesElementExist(activistFilterOn)){ //does nothing if filter is not selected
            int numHolders = getNumOfHoldersDisplayed();
            findElement(activistFilter).click();
            for (int i=0; i<50; i++){ //waits up to 5 seconds for the number of holders displayed to change
                if (getNumOfHoldersDisplayed() != numHolders){
                    return this;
                }
                pause(100);
            }
        }
        return this;
    }

    public int getNumOfActivistsDisplayed(){
        waitForElement(activistIcon);
        List<WebElement> icons = findVisibleElements(activistIcon);
        int numIcons = 0;
        for (WebElement icon : icons){ //only counts icons if they aren't obstructed from view
            try {
                wait.until(ExpectedConditions.elementToBeClickable(icon));
                numIcons++;
            }catch (TimeoutException e){ //will go here if not clickable (because there are fully obstructed)
                System.out.println("Activist icon is not clickable.");
            }
        }
        return numIcons;
    }

    // this method is to be used while one of the date tabs is selected
    public SecurityOwnershipPage showAllTypes(){
        waitForElement(allTypesFilter);
        findVisibleElement(allTypesFilter).click();
        pause(5000);
        return this;
    }

    // this method is to be used while one of the date tabs is selected
    public SecurityOwnershipPage showOnlyInstitutions(){
        waitForElement(institutionsFilter);
        findVisibleElement(institutionsFilter).click();
        pause(5000);
        return this;
    }

    // this method is to be used while one of the date tabs is selected
    public SecurityOwnershipPage showOnlyInsiders(){
        waitForElement(insidersFilter);
        findVisibleElement(insidersFilter).click();
        pause(5000);
        return this;
    }

    // this method is to be used while one of the date tabs is selected
    public SecurityOwnershipPage showOnlyFunds(){
        waitForElement(fundsFilter);
        findVisibleElement(fundsFilter).click();
        pause(5000);
        return this;
    }

    // this method is to be used while one of the date tabs is selected
    // institutions have a red institution icon
    public int getNumOfInstitutionsDisplayed(){
        waitForElement(institutionIcon);
        return findVisibleElements(institutionIcon).size();
    }

    // this method is to be used while one of the date tabs is selected
    // insiders have no QR scores and no institution icons
    public int getNumOfInsidersDisplayed(){
        int numInsiders = 0;
        waitForElement(holderTableRow);
        List<WebElement> rows = findVisibleElements(holderTableRow);
        for (WebElement row : rows){
            if (doesElementExist(By.cssSelector("#"+row.getAttribute("id")+" .rating.no-value"))){ //checks whether there's no QR score within that row
                if (doesElementExist(By.xpath("//div[@id='"+row.getAttribute("id")+"']//i[contains(@class,'q4i-insider-2pt')]"))){ //checks whether there's no institution icon within that row
                    numInsiders++;
                }
            }
        }
        return numInsiders;
    }

    // this method is to be used while one of the date tabs is selected
    // funds have QR scores but no institution icons
    public int getNumOfFundsDisplayed(){
        int numFunds = 0;
        waitForElement(holderTableRow);
        List<WebElement> rows = findVisibleElements(holderTableRow);

        for (WebElement row : rows){
            if (!findVisibleElement(By.cssSelector("#"+row.getAttribute("id")+" .rating")).getAttribute("class").contains("no-value")){ //checks whether there's a QR score within that row
               /* if (doesElementExist(By.xpath("//div[@id='"+row.getAttribute("id")+"']/div[1]/div[1][not(div)]"))){ //checks whether there's no institution icon within that row
                    numFunds++;
                }*/
                if (doesElementExist(By.xpath("//div[@id='"+row.getAttribute("id")+"']//i[contains(@class,'q4i-fund-2pt')]"))){ //checks whether there's no institution icon within that row
                    numFunds++;
                }
            }
        }
        return numFunds;
    }

    public SecurityOwnershipPage showOnlyBuyers(){
        waitForElement(buyersFilter);
        findVisibleElement(buyersFilter).click();
        pause(5000);
        return this;
    }

    public SecurityOwnershipPage showOnlySellers(){
        waitForElement(sellersFilter);
        findVisibleElement(sellersFilter).click();
        pause(5000);
        return this;
    }

    public SecurityOwnershipPage showBuyersAndSellers(){
        waitForElement(allBuyersSellersFilter);
        findVisibleElement(allBuyersSellersFilter).click();
        pause(5000);
        return this;
    }

    public int getNumofBuyersDisplayed(){
        int numBuyers = 0;
        waitForElement(holderTable1W);
        List<WebElement> values = findVisibleElements(holderTable1W);
        for (WebElement value : values){
            if (Integer.parseInt(value.getText().replace(",","")) > 0){
                numBuyers++;
            }
        }
        return numBuyers;
    }

    public int getNumofSellersDisplayed(){
        int numSellers = 0;
        waitForElement(holderTable1W);
        List<WebElement> values = findVisibleElements(holderTable1W);
        for (WebElement value : values){
            if (Integer.parseInt(value.getText().replace(",","")) < 0){
                numSellers++;
            }
        }
        return numSellers;
    }

    //// INSTITUTIONAL OWNERSHIP METHODS \\\\
    
    // checks that the correct number of style bars of each colour are present
    public boolean styleBarsNumsAreCorrect(){
        boolean correct = true;
        int numStyles = 7; //number of styles (including "Other") that should be displayed
        waitForElement(styleBarsYellow);

        if (findVisibleElements(styleBarsBlue).size() != numStyles){
            System.out.println("Incorrect number of blue style bars displayed.\n\tExpected: "+numStyles+"\n\tActual: "+findVisibleElements(styleBarsBlue).size());
            correct = false;
        }
        if (findVisibleElements(styleBarsRed).size() != numStyles){
            System.out.println("Incorrect number of red style bars displayed.\n\tExpected: "+numStyles+"\n\tActual: "+findVisibleElements(styleBarsRed).size());
            correct = false;
        }
        if (findVisibleElements(styleBarsOrange).size() != numStyles){
            System.out.println("Incorrect number of orange style bars displayed.\n\tExpected: "+numStyles+"\n\tActual: "+findVisibleElements(styleBarsOrange).size());
            correct = false;
        }
        if (findVisibleElements(styleBarsYellow).size() != numStyles){
            System.out.println("Incorrect number of yellow style bars displayed.\n\tExpected: "+numStyles+"\n\tActual: "+findVisibleElements(styleBarsBlue).size());
            correct = false;
        }

        return correct;
    }

    // checks that the correct number of turnover bars of each colour are present
    public boolean turnoverBarsNumsAreCorrect(){
        boolean correct = true;
        int numTurnover = 5; //number of turnover types that should be displayed
        waitForElement(turnoverBarsYellow);

        if (findVisibleElements(turnoverBarsBlue).size() != numTurnover){
            System.out.println("Incorrect number of blue turnover bars displayed.\n\tExpected: "+numTurnover+"\n\tActual: "+findVisibleElements(turnoverBarsBlue).size());
            correct = false;
        }
        if (findVisibleElements(turnoverBarsRed).size() != numTurnover){
            System.out.println("Incorrect number of red turnover bars displayed.\n\tExpected: "+numTurnover+"\n\tActual: "+findVisibleElements(turnoverBarsRed).size());
            correct = false;
        }
        if (findVisibleElements(turnoverBarsOrange).size() != numTurnover){
            System.out.println("Incorrect number of orange turnover bars displayed.\n\tExpected: "+numTurnover+"\n\tActual: "+findVisibleElements(turnoverBarsOrange).size());
            correct = false;
        }
        if (findVisibleElements(turnoverBarsYellow).size() != numTurnover){
            System.out.println("Incorrect number of yellow turnover bars displayed.\n\tExpected: "+numTurnover+"\n\tActual: "+findVisibleElements(turnoverBarsBlue).size());
            correct = false;
        }

        return correct;
    }

    // checks that all bars have values between 0 and 100 displayed above them
    public boolean institutionalBarsHaveValidNumbers(){
        waitForElement(institutionalPercentages);
        return elementsAreAllPercentages(findVisibleElements(institutionalPercentages));
    }

    //// TREND ANALYSIS METHODS \\\\

    // Checks whether hovering over the charts causes hovertext to appear
    public boolean canHoverOverTrendAnalysisCharts(){
        boolean canHover = true;
        waitForLoadingScreen();
        waitForElement(trendAnalysisHoverText);
        List<WebElement> charts = findVisibleElements(trendAnalysisChartBody);
        List<WebElement> hovertexts = findElements(trendAnalysisHoverText);

        for(int i=0; i<charts.size(); i++){
            actions.clickAndHold(charts.get(i)).perform(); //clickAndHold needed so that cursor is still there when getAttribute is run
            pause(500);
            if (!hovertexts.get(i).getAttribute("opacity").equals("1")){ // when hovertext is not visible, opacity attribute is either zero or non-existent
                System.out.println("Trend Analysis chart (index "+i+") does have visible hovertext when hovering.");
                canHover = false;
            }
        }

        return canHover;
    }

    //// INSTITUTIONAL HOLDER ANALYSIS METHODS \\\\
    // these methods are to be used while one of the date tabs is selected

    public int getNumOfHolderBreakdownValues(){
        waitForElement(holderBreakdownValues);
        return findVisibleElements(holderBreakdownValues).size();
    }

    // checks that breakdown values are between 0 and 100 (inclusive)
    public boolean holderBreakdownValuesAreValid(){
        waitForElement(holderBreakdownValues);
        return elementsAreAllPercentages(findVisibleElements(holderBreakdownValues));
    }

    public int getNumofHolderTypeValues(){
        waitForElement(holderTypeValues);
        return findVisibleElements(holderTypeValues).size();
    }

    // checks that all type values are between 0 and 100 (inclusive)
    public boolean holderTypeValuesAreValid(){
        waitForElement(holderTypeValues);
        return elementsAreAllPercentages(findVisibleElements(holderTypeValues));
    }

    // clicks on the "Other" option under "Investor Type", causing the dropdown to open
    public SecurityOwnershipPage openOtherHolderTypes(){
        waitForElement(holderTypeOther);
        findVisibleElement(holderTypeOther).click();
        waitForElement(holderOtherDropdown);
        return this;
    }

    public int getNumofHolderStyleValues(){
        waitForElement(holderStyleValues);
        return findVisibleElements(holderStyleValues).size();
    }

    // checks that all style values are between 0 and 100 (inclusive)
    public boolean holderStyleValuesAreValid(){
        waitForElement(holderStyleValues);
        return elementsAreAllPercentages(findVisibleElements(holderStyleValues));
    }

    // clicks on the "Other" option under "Holding Style", causing the dropdown to open
    public SecurityOwnershipPage openOtherHolderStyles(){
        waitForElement(holderStyleOther);
        findVisibleElement(holderStyleOther).click();
        waitForElement(holderOtherDropdown);
        return this;
    }

    public int getNumofHolderTurnoverValues(){
        waitForElement(holderTurnoverValues);
        return findVisibleElements(holderTurnoverValues).size();
    }

    // checks that all turnover values are between 0 and 100 (inclusive)
    public boolean holderTurnoverValuesAreValid(){
        waitForElement(holderTurnoverValues);
        return elementsAreAllPercentages(findVisibleElements(holderTurnoverValues));
    }

    // clicks on the "Other" option under "Turnover", causing the dropdown to open
    public SecurityOwnershipPage openOtherHolderTurnovers(){
        waitForElement(holderTurnoverOther);
        findVisibleElement(holderTurnoverOther).click();
        waitForElement(holderOtherDropdown);
        return this;
    }

    // checks that there are at least two values in the other dropdown
    public boolean otherHolderValuesArePresent(){
        waitForElement(holderOtherValues);
        return findVisibleElements(holderOtherValues).size() > 1;
    }

    // checks that all of the values in the other dropdown are between 0 and 100 (inclusive)
    public boolean otherHolderValuesAreValid(){
        waitForElement(holderOtherValues);
        return elementsAreAllPercentages(findVisibleElements(holderOtherValues));

    }

    // closes the other dropdown by clicking outside it
    public SecurityOwnershipPage closeOtherHolderDropdown(){
        waitForElement(holderOtherDropdown);
        actions.moveToElement(findElement(holderOtherDropdown), -10, -10).click().perform();
        pause(3000);
        return this;
    }

    // checks that the other dropdown has been closed
    public boolean otherHolderDropdownIsClosed(){
        return !doesElementExist(holderOtherDropdown);
    }

    public SecurityOwnershipPage viewHistoricalHolders() {
        waitForLoadingScreen();
        findElement(historicalTab).click();
        waitForLoadingScreen();

        return this;
    }

    public SecurityOwnershipPage viewCurrentHolders(){
        waitForLoadingScreen();
        findElement(currentTab).click();

        return this;
    }

    public SecurityOwnershipPage searchForHoldings(String searchTerm) {
        findElement(holdingsSearchField).sendKeys(searchTerm);
        waitForLoadingScreen();
        // Added pause to account for the situation where the test will take the original top value of the table and won't wait for the results to be updated by the search.
        pause(3000);
        return this;
    }

    //different types of Holder Searches
    public String getHistoricalInstitutionsHolderSearchResults() {
        waitForElementToAppear(historicalInstitutionsHolderSearchResult);
        return findElement(historicalInstitutionsHolderSearchResult).getText();
    }

    public String getCurrentInsidersHolderSearchResults() {
        waitForElementToAppear(currentInsidersHolderSearchResult);
        return findElement(currentInsidersHolderSearchResult).getText();
    }

    public String getHistoricalFundsHolderSearchResults(){
        waitForElementToAppear(historicalFundsHolderSearchResults);
        return findElement(historicalFundsHolderSearchResults).getText();
    }

    public SecurityOwnershipPage searchForFundsETFs(String searchTerm) {
        findElement(holdingsSearchField).sendKeys(searchTerm);
        // Added pause to account for the situation where the test will take the original top value of the table and won't wait for the results to be updated by the search.
        pause(3000);
        return this;
    }

    public SecurityOwnershipPage selectFundsETFstab() {
        waitForLoadingScreen();
        findElement(FundsETFsTab).click();

        return this;
    }

    public String getHolderSearchResultstwo() {
        waitForElementToAppear(holderSearchResulttwo);
        return findElement(holderSearchResulttwo).getText();
    }

    public SecurityOwnershipPage viewInstitutiontab() {
        waitForLoadingScreen();
        findElement(InstitutionTab).click();

        return this;
    }

    public String getInstitutionSearchResults() {
        waitForElementToAppear(InstitutionSearchResult);
        return findElement(InstitutionSearchResult).getText();
    }

    public void selectInsiderstab() {
        waitForLoadingScreen();
        findElement(InsidersTab).click();

    }

    public String getInsiderSearchResults() {
        waitForElementToAppear(InsiderSearchResult);
        return findElement(InsiderSearchResult).getText();
    }

    public String getHolderSearchResults() {
        waitForElementToAppear(holderSearchResult);
        return findElement(holderSearchResult).getText();
    }

    public SecurityOwnershipPage searchForFundsETFs(String searchTerm) {
        findElement(holdingsSearchField).sendKeys(searchTerm);
        // Added pause to account for the situation where the test will take the original top value of the table and won't wait for the results to be updated by the search.
        pause(3000);
        return this;
    }

    public SecurityOwnershipPage selectFundsETFstab() {
        waitForLoadingScreen();
        findElement(FundsETFsTab).click();

        return this;
    }

    public String getHolderSearchResultstwo() {
        waitForElementToAppear(holderSearchResulttwo);
        return findElement(holderSearchResulttwo).getText();
    }

    public SecurityOwnershipPage viewInstitutiontab() {
        waitForLoadingScreen();
        findElement(InstitutionTab).click();

        return this;
    }

    public String getInstitutionSearchResults() {
        waitForElementToAppear(InstitutionSearchResult);
        return findElement(InstitutionSearchResult).getText();
    }

    public void selectInsiderstab() {
        waitForLoadingScreen();
        findElement(InsidersTab).click();

    }

    public String getInsiderSearchResults() {
        waitForElementToAppear(InsiderSearchResult);
        return findElement(InsiderSearchResult).getText();
    }
}
