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

    private final By addSymbolInput = By.name("symbol");
    private final By addSymbolResultList = By.xpath("//div[contains(@class,'filter-by-company-results')]");
    private final By addSymbolResultItem = By.xpath("//div[contains(@class,'result-item')]");
    private final By addedSecurity = By.xpath("//div[contains(@class,'saved-company')]");

    private final By previousDateRange = By.xpath("//div[contains(@class,'range-navigator') and span[contains(@class,'q4i-arrow-left-2pt')]]");
    private final By nextDateRange = By.xpath("//div[contains(@class,'range-navigator') and span[contains(@class,'q4i-arrow-right-2pt')]]");
    private final By searchInput = By.xpath("//div[contains(@class,'x-docked-right')]//input[contains(@class,'x-input-search')]");
    private final By callsHeader = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Calls')]]");
    private final By callsEarnings =By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Calls')]]/following-sibling::div[div[div[contains(@class,'filter-type') and contains(text(),'Earnings')]]][1]");
    private final By callsGuidance = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Calls')]]/following-sibling::div[div[div[contains(@class,'filter-type') and contains(text(),'Guidance')]]][1]");
    private final By callsSalesAndEarnings = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Calls')]]/following-sibling::div[div[div[contains(@class,'filter-type') and contains(text(),'Sales')]]][1]");
    private final By releasesHeader = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Releases')]]");
    private final By releasesEarnings = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Releases')]]/following-sibling::div[div[div[contains(@class,'filter-type') and contains(text(),'Earnings')]]][1]");
    private final By releasesSalesAndEarnings = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Releases')]]/following-sibling::div[div[div[contains(@class,'filter-type') and contains(text(),'Sales')]]][1]");
    private final By miscHeader = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Misc')]]");
    private final By miscConferences =  By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Misc')]]/following-sibling::div[div[div[contains(@class,'filter-type') and contains(text(),'Conferences')]]][1]");
    private final By miscPresentations = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Misc')]]/following-sibling::div[div[div[contains(@class,'filter-type') and contains(text(),'Presentations')]]][1]");
    private final By miscMeetings = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Misc')]]/following-sibling::div[div[div[contains(@class,'filter-type') and contains(text(),'Meetings')]]][1]");
    private final By miscOther = By.xpath("//div[contains(@class,'event-sidebar')]//div[contains(@class,'x-list-item') and .//div[contains(text(),'Misc')]]/following-sibling::div[div[div[contains(@class,'filter-type') and contains(text(),'Other')]]][1]");

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


    public boolean wasSecurityAdded(String ticker){
        try {
            waitForAnyElementToAppear(addedSecurity);
            List<WebElement> securities = findVisibleElements(addedSecurity);
            for (WebElement security : securities) {
                if (security.getText().contains(ticker)) {
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            return false;
        }

    }

    public EventsTranscriptsPage addSymbol(String ticker, String exchange){
        List<WebElement> results;
        waitForElementToBeClickable(addSymbolInput).sendKeys(ticker);
        waitForElementToAppear(addSymbolResultList);
        waitForElementToBeClickable(addSymbolResultItem);
        results = new ArrayList<>(findVisibleElements(addSymbolResultItem));
        for(WebElement result : results){
            if(result.findElement(By.xpath(".//span[contains(@class,'symbol')]")).getText().contains(ticker) && result.findElement(By.xpath(".//span[contains(@class,'exchange')]")).getText().contains(exchange)){
                result.click();
                waitForLoadingScreen();
                return this;
            }
        }
        return null;
    }

}
