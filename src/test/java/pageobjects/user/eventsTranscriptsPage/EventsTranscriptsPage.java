package pageobjects.user.eventsTranscriptsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickp on 2016-08-16.
 */
public class EventsTranscriptsPage extends AbstractPageObject {

    private final By addSymbolInput = By.name("symbol");
    private final By addSymbolResultList = By.xpath("//div[contains(@class,'filter-by-company-results')]");
    private final By addSymbolResultItem = By.xpath("//div[contains(@class,'result-item')]");
    private final By addedSecurity = By.xpath("//div[contains(@class,'saved-company')]");
    private final By watchListToggle = By.xpath("//div[contains(@class,'toggler')]//div[span[contains(text(),'Watchlist')]]");
    private final By transcriptToggle= By.xpath("//div[contains(@class,'toggler')]//div[span[contains(text(),'Transcript')]]");
    private final By todayButton = By.xpath("//div[contains(@class,'range-navigator') and contains(@class,'today')]");
    private final By previousDateRange = By.xpath("//div[contains(@class,'range-navigator') and span[contains(@class,'q4i-arrow-left-2pt')]]");
    private final By nextDateRange = By.xpath("//div[contains(@class,'range-navigator') and span[contains(@class,'q4i-arrow-right-2pt')]]");
    private final By dayRange = By.xpath("//div[contains(@class,'range-tabs')]//div[contains(@class,'x-button') and span[text()='Day']]");
    private final By weekRange =By.xpath("//div[contains(@class,'range-tabs')]//div[contains(@class,'x-button') and span[text()='Week']]");
    private final By monthRange =By.xpath("//div[contains(@class,'range-tabs')]//div[contains(@class,'x-button') and span[text()='Month']]");
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

    public EventsTranscriptsPage(WebDriver driver){super(driver);}

//div[contains(@class,'result-item')]


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
