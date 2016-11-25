package pageobjects.user.securityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

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
}
