package pageobjects.user.securityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    private final By activistFilter = By.cssSelector(".ownership-report-top-holders .toggle-button");
    private final By activistFilterOn = By.cssSelector(".ownership-report-top-holders .toggle-button .x-toggle-on");
    private final By activistFilterOff = By.cssSelector(".ownership-report-top-holders .toggle-button .x-toggle-off");
    private final By allTypesFilter = By.cssSelector("span.q4i-list-2pt");
    private final By institutionsFilter = By.cssSelector("span.q4i-institution-2pt");
    private final By insidersFilter = By.cssSelector("span.q4i-insider-2pt");
    private final By fundsFilter = By.cssSelector("span.q4i-fund-2pt");
    private final By allBuyersSellersFilter = By.cssSelector(".ownership-report-main-content .range-tabs .x-button:first-child");
    private final By buyersFilter = By.cssSelector(".ownership-report-main-content .range-tabs .x-button:nth-child(2)");
    private final By sellersFilter = By.cssSelector(".ownership-report-main-content .range-tabs .x-button:nth-child(3)");
    private final By holderTableHeaderName = By.cssSelector(".x-grid-column:first-child");
    private final By holderTableHeaderPOS = By.cssSelector(".x-grid-column:nth-child(2)");
    private final By holderTableHeader1QChg = By.cssSelector(".x-grid-column:nth-child(3)");
    private final By holderTableHeaderMktVal = By.cssSelector(".x-grid-column:nth-child(4)");
    private final By holderTableHeaderMktValChg = By.cssSelector(".x-grid-column:nth-child(5)");
    private final By holderTableHeaderPercOS = By.cssSelector(".x-grid-column:nth-child(6)");
    private final By holderTableHeaderPercPort = By.cssSelector(".x-grid-column:nth-child(7)");
    private final By holderTableHeaderStyle = By.cssSelector(".x-grid-column:nth-child(8)");
    private final By holderTableHeaderTurnover = By.cssSelector(".x-grid-column:nth-child(9)");
    private final By holderTableHeaderAUM = By.cssSelector(".x-grid-column:nth-child(10)");
    private final By holderTableHeaderAsOf = By.cssSelector(".x-grid-column:nth-child(11)");
    private final By holderTableHeaderQR = By.cssSelector(".x-grid-column:nth-child(13)");
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
    private final By holderTableQR = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(13)");
    private final By holderTable1W = By.cssSelector(".x-dataview-item .view-list-item:nth-child(4)"); // only exists when using Buyers or Sellers filter
    private final By showMoreButton = By.className("q4i-arrow-down-2pt");
    private final By activistIcon = By.cssSelector(".icon.activists");
    private final By institutionIcon = By.cssSelector("i.q4i-institution-2pt");

    private final DateTimeFormatter shortDate = DateTimeFormatter.ofPattern("MM/dd/yy");
    private final DateTimeFormatter longDate = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    private final LocalDate today = LocalDate.now();

    public SecurityOwnershipPage(WebDriver driver) {
        super(driver);
    }

    public String getOwnershipTabTitle() {
        pause(2000L);
        waitForElementToAppear(tabTitle);
        return findElement(tabTitle).getText();
    }

    //// UNLESS OTHERWISE INDICATED, ALL METHODS ON THIS PAGE ARE DESIGNED TO OCCUR WHEN THE SURVEILLANCE TAB IS SELECTED. \\\\
    //      (They may or may not work while one of the date options is selected.)

    // index goes from 0 to 4, with 0 being end of last quarter
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
            endOfQuarter = today.minusYears(1).with(TemporalAdjusters.lastDayOfYear());;
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

    public boolean canSortHoldersTable(){
        boolean isSorted = true;

        // sorting by name ascending
        findVisibleElement(holderTableHeaderName).click();
        pause(200);
        if (!elementsAreAlphaUpSorted(findElements(holderTableName))){
            System.out.println("Holders are not sorted by name ascending.");
            isSorted = false;
        }

        // sorting by name descending
        findVisibleElement(holderTableHeaderName).click();
        pause(200);
        if (!elementsAreAlphaDownSorted(findElements(holderTableName))){
            System.out.println("Holders are not sorted by name descending.");
            isSorted = false;
        }

        // sorting by POS ascending
        findVisibleElement(holderTableHeaderPOS).click();
        pause(200);
        if (!elementsAreNumUpSorted(findElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS ascending.");
            isSorted = false;
        }

        // sorting by POS descending
        findVisibleElement(holderTableHeaderPOS).click();
        pause(200);
        if (!elementsAreNumDownSorted(findElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS descending.");
            isSorted = false;
        }

        // sorting by 1Q change ascending
        findVisibleElement(holderTableHeader1QChg).click();
        pause(200);
        if (!elementsAreNumUpSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change ascending.");
            isSorted = false;
        }

        // sorting by 1Q change descending
        findVisibleElement(holderTableHeader1QChg).click();
        pause(200);
        if (!elementsAreNumDownSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change descending.");
            isSorted = false;
        }

        // sorting by market value ascending
        findVisibleElement(holderTableHeaderMktVal).click();
        pause(200);
        if (!elementsAreNumUpSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value ascending.");
            isSorted = false;
        }

        // sorting by market value descending
        findVisibleElement(holderTableHeaderMktVal).click();
        pause(200);
        if (!elementsAreNumDownSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value descending.");
            isSorted = false;
        }

        // sorting by market value change ascending
        findVisibleElement(holderTableHeaderMktValChg).click();
        pause(200);
        if (!elementsAreNumUpSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change ascending.");
            isSorted = false;
        }

        // sorting by market value change descending
        findVisibleElement(holderTableHeaderMktValChg).click();
        pause(200);
        if (!elementsAreNumDownSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change descending.");
            isSorted = false;
        }

        // sorting by %OS ascending
        findVisibleElement(holderTableHeaderPercOS).click();
        pause(200);
        if (!elementsAreNumUpSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS ascending.");
            isSorted = false;
        }

        // sorting by %OS descending
        findVisibleElement(holderTableHeaderPercOS).click();
        pause(200);
        if (!elementsAreNumDownSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS descending.");
            isSorted = false;
        }

        // sorting by %Port ascending
        findVisibleElement(holderTableHeaderPercPort).click();
        pause(200);
        if (!elementsAreNumUpSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port ascending.");
            isSorted = false;
        }

        // sorting by %Port descending
        findVisibleElement(holderTableHeaderPercPort).click();
        pause(200);
        if (!elementsAreNumDownSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port descending.");
            isSorted = false;
        }

        // sorting by style ascending
        findVisibleElement(holderTableHeaderStyle).click();
        pause(200);
        if (!elementsAreAlphaUpSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style ascending.");
            isSorted = false;
        }

        // sorting by style descending
        findVisibleElement(holderTableHeaderStyle).click();
        pause(200);
        if (!elementsAreAlphaDownSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style descending.");
            isSorted = false;
        }

        // sorting by turnover ascending
        findVisibleElement(holderTableHeaderTurnover).click();
        pause(200);
        if (!elementsAreTurnoverUpSorted(findElements(holderTableTurnover))){
            System.out.println("Holders are not sorted by turnover ascending.");
            isSorted = false;
        }

        // sorting by turnover descending
        findVisibleElement(holderTableHeaderTurnover).click();
        pause(200);
        if (!elementsAreTurnoverDownSorted(findElements(holderTableTurnover))){
            System.out.println("Holders are not sorted by turnover descending.");
            isSorted = false;
        }

        // sorting by AUM ascending
        findVisibleElement(holderTableHeaderAUM).click();
        pause(200);
        if (!elementsAreNumUpSorted(findElements(holderTableAUM))){
            System.out.println("Holders are not sorted by AUM ascending.");
            isSorted = false;
        }

        // sorting by AUM descending
        findVisibleElement(holderTableHeaderAUM).click();
        pause(200);
        if (!elementsAreNumDownSorted(findElements(holderTableAUM))){
            System.out.println("Holders are not sorted by AUM descending.");
            isSorted = false;
        }

        // sorting by as of date ascending
        findVisibleElement(holderTableHeaderAsOf).click();
        pause(200);
        if (!elementsAreDateUpSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date ascending.");
            isSorted = false;
        }

        // sorting by as of date descending
        findVisibleElement(holderTableHeaderAsOf).click();
        pause(200);
        if (!elementsAreDateDownSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date descending.");
            isSorted = false;
        }

        // sorting by QR ascending
        findVisibleElement(holderTableHeaderQR).click();
        pause(200);
        if (!elementsAreNumUpSorted(findElements(holderTableQR))){
            System.out.println("Holders are not sorted by QR ascending.");
            isSorted = false;
        }

        // sorting by QR descending
        findVisibleElement(holderTableHeaderQR).click();
        pause(200);
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
                if (doesElementExist(By.xpath("//div[@id='"+row.getAttribute("id")+"']/div[1]/div[1][not(div)]"))){ //checks whether there's no institution icon within that row
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
                if (doesElementExist(By.xpath("//div[@id='"+row.getAttribute("id")+"']/div[1]/div[1][not(div)]"))){ //checks whether there's no institution icon within that row
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

}
