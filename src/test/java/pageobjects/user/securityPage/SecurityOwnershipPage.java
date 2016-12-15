package pageobjects.user.securityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * Created by patrickp on 2016-08-24.
 */
public class SecurityOwnershipPage extends AbstractPageObject {
    private final By tabTitle = By.cssSelector(".company-header .menu-button .x-button-label");
    private final By dateOption = By.cssSelector(".company-ownership-inner .range-tabs-inner .x-button-no-icon");
    private final By asOfDate = By.cssSelector(".company-ownership-inner .disclaimer span");
    private final By topBuyersNumbers = By.cssSelector(".top-buyers-list .inst-value:not(:empty)");
    private final By topSellersNumbers = By.cssSelector(".top-sellers-list .inst-value:not(:empty)");
    private final By topBuyersAndSellers = By.cssSelector(".list-item .inst-name");
    private final By lastTopSeller = By.cssSelector(".company-ownership-inner > div > div > div:not(.x-hidden-display) .top-sellers-list .x-dataview-item:nth-child(5) .list-item");

    // holders table
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
    private final By showMoreButton = By.className("q4i-arrow-down-2pt");

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

    public SecurityOwnershipPage selectDate(int index){
        waitForElement(dateOption);
        findElements(dateOption).get(index).click();
        pause(500);
        wait.until(ExpectedConditions.elementToBeClickable(lastTopSeller));
        return this;
    }

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

        if (!LocalDate.parse(findElement(asOfDate).getText(), longDate).equals(lastFriday)){
            System.out.println("As of date while in surveillance mode is incorrect.\n\tExpected: "+lastFriday.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }
        selectDate(0);
        pause(500);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting first date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }
        selectDate(1);
        pause(500);
        endOfQuarter = endOfQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());
        startOfQuarter = startOfQuarter.minusMonths(3);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting second date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }
        selectDate(2);
        pause(500);
        endOfQuarter = endOfQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());
        startOfQuarter = startOfQuarter.minusMonths(3);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting third date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }
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

    public boolean topBuyersListIsPositive(){
        waitForElement(topBuyersNumbers);
        return elementsAreAllPositive(findVisibleElements(topBuyersNumbers));
    }

    public boolean topSellersListIsNegative(){
        waitForElement(topSellersNumbers);
        return elementsAreAllNegative(findVisibleElements(topSellersNumbers));
    }

    public boolean topBuyersListIsDescending(){
        waitForElement(topBuyersNumbers);
        return elementsAreNumDownSorted(findVisibleElements(topBuyersNumbers));
    }

    public boolean topSellersListIsAscending(){
        waitForElement(topSellersNumbers);
        return elementsAreNumUpSorted(findVisibleElements(topSellersNumbers));
    }

    public boolean topBuyersAndSellersAreUnique(){
        waitForElement(topBuyersAndSellers);
        return elementsDoNotContainDuplicates(findVisibleElements(topBuyersAndSellers));
    }

    public int getNumOfHoldersDisplayed(){
        waitForElement(holderTableName);
        return findVisibleElements(holderTableName).size();
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
}
