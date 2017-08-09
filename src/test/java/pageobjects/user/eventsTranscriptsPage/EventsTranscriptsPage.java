package pageobjects.user.eventsTranscriptsPage;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by dannyl on 2017-08-08.
 */
public class EventsTranscriptsPage extends AbstractPageObject {

    private final By dayFilterButton =By.xpath("//span[contains(text(), 'Day')]");
    private final By weekFilterButton = By.xpath("//span[contains(text(), 'Week')]");
    private final By monthFilterButton = By.xpath("//span[contains(text(), 'Month')]");
    private final By todayFilterButton = By.xpath("//span[contains(text(), 'Today')]");
    private final By watchlistToggle = By.id("ext-thumb-3");
    private final By transcriptToggle = By.id("ext-thumb-4");
    private final By dateHeaders = By.xpath("//div[contains(@class, 'group-header')]");
    private final By dateFilter = By.xpath("//div[contains(@class, 'x-size-monitored x-paint-monitored current-date-range x-layout-box-item x-stretched')]");
    private final By transcriptIcon = By.xpath("//i[contains(@class, 'q4i-transcripts-2pt')]");

    public EventsTranscriptsPage(WebDriver driver) {super(driver);}

    private ArrayList<String> returnDateHeaders (){
        List<WebElement> headerList = findVisibleElements(dateHeaders);
        ArrayList<String> dates = new ArrayList<>();
        for (WebElement i : headerList){
            dates.add(i.getText());
        }
        return dates;
    }

    private List<WebElement> returnRows (){
        List<WebElement> rowList = findVisibleElements(By.xpath("//div[contains(@class, 'x-unsized x-list-item x-list-item-tpl event-list-item x-list-item-relative')]/div[contains(@class, 'x-innerhtml')]"));
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;

    }

    public Boolean filterByDay(){
        waitForElementToBeClickable(dayFilterButton).click();
        waitForElementToBeClickable(todayFilterButton).click();
        waitForElementToBeClickable(watchlistToggle).click();
        waitForLoadingScreen();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        String date = dateFormat.format(today);

        return checkFilter(date);
    }

    public Boolean filterByMonth(){
        waitForElementToBeClickable(todayFilterButton).click();
        waitForElementToBeClickable(monthFilterButton).click();
        waitForElementToBeClickable(watchlistToggle).click();
        waitForLoadingScreen();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        String date = dateFormat.format(today);

        return checkFilter(date);
    }

    public Boolean filterByWeek(){
        waitForElementToBeClickable(todayFilterButton).click();
        waitForElementToBeClickable(weekFilterButton).click();
        waitForElementToBeClickable(watchlistToggle).click();
        waitForLoadingScreen();

        String date = waitForElementToAppear(dateFilter).getText();
        int index = date.indexOf("- ");
        if (index != -1)
        {
        date = date.substring(index + 2); // +2 because we want to get rid of the dash and space
        }

        return checkFilter(date);
    }

    public Boolean filterByToday(){
        waitForElementToBeClickable(todayFilterButton).click();
        waitForLoadingScreen();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        String date = dateFormat.format(today);

        return checkFilter(date);
    }

    public Boolean filterByTranscript(){
        waitForElementToBeClickable(transcriptToggle).click();
        waitForElementToBeClickable(watchlistToggle).click();
        waitForLoadingScreen();

        return checkFilterForTranscript();
    }

    public boolean checkFilter(String date){
        ArrayList<String> expectedDates = returnDateHeaders();
        for (String i : expectedDates){
            if (!(i.toLowerCase().contains(date.toLowerCase()))){
                return false;
            }
        }
        return true;
    }

    public boolean checkFilterForTranscript(){
        List<WebElement> transcripts = returnRows();
        for (WebElement i : transcripts){
            try {
                i.findElement(transcriptIcon);
            }
            catch (ElementNotFoundException e)
            {
                return false;
            }
        }
        return true;
    }

}
