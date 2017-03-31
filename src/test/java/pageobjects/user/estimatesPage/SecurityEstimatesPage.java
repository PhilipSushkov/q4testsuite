package pageobjects.user.estimatesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.user.Calendar;

/**
 * Created by patrickp on 2016-08-24.
 */
public class SecurityEstimatesPage extends AbstractPageObject{

    private final By tabTitle = By.cssSelector(".company-header .menu-button .x-button-label");

    //anchor links
    private final By researchAnchorLink = By.xpath("//div[contains(@class,'anchor-toolbar')]//span[contains(string(),'Research')]");

    //for research column headers
    private final By researchDateColumnHeader = By.xpath("//div[contains(@class,'estimates-research-list')]//span[contains(string(),'Date')]");
    private final By firstResearchDate = By.xpath("//div[contains(@class,'estimates-research-list')]//div[1][contains(@class,'x-dataview-item')]//div[contains(@class,'column date')]");

    //for calendars
    private final By startTimeSelector = By.xpath("//input[contains(@name,'startDate')]");
    private final By endTimeSelector = By.xpath("//input[contains(@name,'endDate')]");
    private final By previousMonthButton = By.xpath("//div[@class='pmu-prev pmu-button']");
    private final By selectedMonth = By.xpath("//div[@class='pmu-month pmu-button']");
    private final By selectedDay = By.xpath("//div[@class='pmu-days']/div[@class='pmu-button'][11]");
    private final By dateFilterButton = By.xpath("//span[contains(string(),'GO')]");

    public SecurityEstimatesPage(WebDriver driver) {
        super(driver);
    }

    public String getEstimatesTabTitle() {

        waitForLoadingScreen();
        return findElement(tabTitle).getText();
    }


    public Calendar filterDate(Calendar calendar) {
        waitForLoadingScreen();
        waitForElement(researchAnchorLink);
        findElement(researchAnchorLink).click();
        calendar.selectStartDate(startTimeSelector, previousMonthButton, selectedMonth, selectedDay);
        calendar.selectEndDate(endTimeSelector, previousMonthButton, selectedMonth, selectedDay);
        calendar.filter(dateFilterButton);
        pause(500L);
        // helps keep track of which days were selected
        calendar.print();
        return calendar;
    }

    public boolean sortByDateRange(Calendar calendar) {
        waitForLoadingScreen();
        // sorting the date by earliest to latest
        findVisibleElement(researchDateColumnHeader).click();
        pause(200L);
        boolean Sorted = true;
        if (!calendar.EarliestDateWithinRange(findVisibleElement(firstResearchDate).getText())) {
            System.out.println("Earliest date in the table is earlier than the selected end time");
            Sorted = false;
        }

        // sorting the date by latest to earliest
        findVisibleElement(researchDateColumnHeader).click();
        pause(200L);
        if (!calendar.latestDateWithinRange(findVisibleElement(firstResearchDate).getText())) {
            System.out.println("Latest date in the table is later than the selected end time");
            Sorted = false;
        }
        return Sorted;
    }
}
