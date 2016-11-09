package pageobjects.user.securityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageobjects.user.watchlist.WatchlistPage;

/**
 * Created by patrickp on 2016-08-04.
 */
public class SecurityOverviewPage extends WatchlistPage {


    private final By overviewModal = By.cssSelector(".company-page .company-slide-inner");  //Create an interface including these things for overview page
    private final By ownershipModal = By.cssSelector(".company-ownership .company-ownership-inner");
    /**
     * HEADER
     */


    //data\\

    private final By companyName = By.className("company-name");
    private final By companyTicker = By.className("company-symbol");
    private final By industry_Exchange = By.className("exchange");
    private final By stockQuote = By.className("stock-price");
    private final By changeIcon = By.className("change-icon");
    private final By stockChange = By.className("change-value");

    private final By volume = By.xpath("//*[@class=\"value\"]");
    private final By avgVolume = By.xpath("(//span[@class='value'])[2]");   // <- Follow this syntax!!!

    private final By recentEstimatesResults = By.cssSelector(".company-header-latest-estimates .latest-estimate-item");
    private final By recentEventsResults = By.cssSelector(".company-header-latest-events .latest-events-item");
    private final By recentTranscriptsResults = By.cssSelector(".company-header-latest-transcripts .latest-transcripts-item");
    private final By recentNewsResults = By.cssSelector(".company-header-latest-news .news-item");
    //dropdown\\

    final By dropdownModal = By.cssSelector(".company-service-list .company-service-list-container");

    final By dropdownMenu = By.cssSelector(".company-header .menu-button .x-button-label");
    final By dropdownLeftArrow = By.cssSelector(".company-header .navigate-button:first-child");
    final By dropdownRightArrow = By.cssSelector(".company-header .navigate-button:last-child");

    final By dropdownOverview = By.xpath("//*[@class=\"company-service-list-item menu-item\"][1]");
    final By dropdownOwnership = By.xpath("//*[@class=\"company-service-list-item menu-item\"][2]");
    final By dropdownTrading = By.xpath("//*[@class=\"company-service-list-item menu-item\"][3]");
    final By dropdownSentiment = By.xpath("//*[@class=\"company-service-list-item menu-item\"][4]");
    final By dropdownVolatility = By.xpath("//*[@class=\"company-service-list-item menu-item\"][5]");
    final By dropdownActivism = By.xpath("//*[@class=\"company-service-list-item menu-item\"][6]");
    final By dropdownPerformance = By.xpath("//*[@class=\"company-service-list-item menu-item\"][7]");
    final By dropdownEstimates = By.xpath("//*[@class=\"company-service-list-item menu-item\"][8]");

    //buttons\\ -> to add stuff for modals that appear when clicking button

    private final By recentEstimatesBtn = By.cssSelector(".company-header .header-notifications-tab .x-button:first-child");
    private final By recentEventsBtn = By.cssSelector(".company-header .header-notifications-tab .x-button + .x-button");
    private final By recentTranscriptsBtn = By.className("x-button x-iconalign-center x-sized transcripts-button");
    private final By recentNewsBtn = By.cssSelector(".company-header .header-notifications-tab .x-button:last-child");

    //three_point_button\\ -> to add stuff for modals that appear when clicking button

    private final By threePointBtn = By.className("x-button-icon x-shown q4i-utility-4pt");

    private final By watchlistBtn = By.className("x-unsized x-button x-text-align-left x-iconalign-left");
    private final By logActivityBtn = By.xpath("//*[@class=\"x-unsized x-button x-iconalign-left x-text-align-left\"][1]");
    private final By suggestEditBtn = By.xpath("//*[@class=\"x-unsized x-button x-iconalign-left x-text-align-left\"][2]");

    //tags\\ -> TESTING THIS WILL BE TRICKY. FOR NOW, TEST FOR CREATION OF ONE TAG

    private final By addTagBtn = By.className("x-button x-stretched tag add-button x-iconalign-center");
    private final By createdTagBtn = By.className("tag"); //Can get text from this
    private final By removeTagBtn = By.className("delete");


    /**
     * CHARTS
     */

    //buttons\\

    private final By oneMonthBtn = By.xpath("//*[@class=\"highcharts-button\"][1]");
    private final By threeMonthBtn = By.xpath("//*[@class=\"highcharts-button\"][2]");
    private final By oneYearBtn = By.xpath("//*[@class=\"highcharts-button\"][3]");

    private final By s_P500Btn = By.className("x-button x-button-no-icon legend-sp500 x-layout-box-item x-stretched");
    private final By nasdaqBtn = By.className("x-button x-button-no-icon legend-nasdaq x-layout-box-item x-stretched");
    private final By dowjonesBtn = By.className("x-button x-button-no-icon legend-dowjones x-layout-box-item x-stretched");
    private final By indicatorsBtn = By.className("x-button x-iconalign-left legend-indicators x-layout-box-item x-stretched");

    private final By sentimentBtn = By.className("indicator-item osi");
    private final By volatilityBtn = By.className("indicator-item ovi");
    private final By activismBtn = By.className("indicator-item oaa");
    private final By performanceBtn = By.className("indicator-item rp");

    private final By toggleBtn = By.cssSelector(".switch-view-button .x-toggle .x-thumb");

    //data\\ -> Found once toggled

    private final By prevCloseValue = By.xpath("//*[@class=\"value\"][3]");
    private final By openValue = By.xpath("//*[@class=\"value\"][7]");
    private final By intradayLowValue1 = By.xpath("//*[@class=\"value\"][4]");
    private final By intradayLowValue2 = By.xpath("//*[@id=\"day-bg-group\"]/text[2]"); //Found on 1d chart, above "LOW"
    private final By intradayHighValue1 = By.xpath("//*[@class=\"value\"][8]");
    private final By intradayHighValue2 = By.xpath("//*[@id=\"day-bg-group\"]/text[3]"); //Found on 1d chart, above "HIGH"
    private final By yearLowValue1 = By.xpath("//*[@class=\"value\"][5]"); //year Low = 52wk Low
    private final By yearLowValue2 = By.xpath("//*[@id=\"week-bg-group\"]/text[2]"); //Found on 52wk chart, above "LOW"
    private final By yearHighValue1 = By.xpath("//*[@class=\"value\"][9]");
    private final By yearHighValue2 = By.xpath("//*[@id=\"week-bg-group\"]/text[3]"); //Found on 52wk chart, above "HIGH"
    private final By dailyVolumeValue = By.xpath("//*[@class=\"value\"][6]");
    private final By currencyType = By.xpath("//*[@class=\"value\"][10]");


    /**
     * QUALITY RATING
     */

    private final By qualityRatingValue = By.cssSelector(".overview-quality-rating .qr-value .x-innerhtml:not(:empty)");

    //investor_stlye_data\\

    private final By growthValue = By.xpath("//*[@class=\"val\"][22]");
    private final By indexValue = By.xpath("//*[@class=\"val\"][23]");
    private final By yieldValue = By.xpath("//*[@class=\"val\"][24]");
    private final By investorStyleOtherValue = By.xpath("//*[@class=\"val\"][25]");

    //turnover_data\\

    private final By veryLowValue = By.xpath("//*[@class=\"val\"][26]");
    private final By lowValue = By.xpath("//*[@class=\"val\"][27]");
    private final By mediumValue = By.xpath("//*[@class=\"val\"][28]");
    private final By turnoverOtherValue = By.xpath("//*[@class=\"val\"][29]");

    //buttons\\

    private final By ownershipBtn = By.cssSelector(".overview-quality-rating"); //This is the Ownership Quality Rating modal, excluding EPS/SALES
    //private final By epsBtn = //EPS value to be found here
    //private final By salesBtn = //The Sales value to be found here.


    /**
     * MINI CHARTS
     */

    //expected_trading_range\\

    private final By hiValue = By.xpath("//*[@class=\"val\"][18]");
    private final By loValue = By.xpath("//*[@class=\"val\"][19]"); //click on this for redirection

    //relative_performance\\

    private final By actualValue = By.xpath("//*[@class=\"val\"][20]");
    private final By stockValue = By.xpath("//*[@class=\"val\"][21]"); //click on this for redirection

    //sentiment\\

    private final By sentimentValue = By.xpath("//*[contains(@class, 'peer-piechart')]"); //Click on value for redirection

    //volatility\\

    private final By volatilityValue = By.xpath("//*[contains(@class, 'volatility-chart')]"); //Click on value for redirection

    //activism\\

    private final By activismValue = By.xpath("//*[contains(@class,'activism-chart')]"); //Click on value for redirection


    /**       EVENTS & TRANSCRIPTS       */ //NO TEST CASES YET!!!


    //private final By allBtn
    //private final By transcriptsBtn
    //private final By webcastsBtn
    //private final By slidesBtn
    //private final By releasesBtn
    //private final By secBtn

    //private final By events_TranscriptsBtn
    //private final By newsBtn

    /**       NEWS      */ //NO TEST CASES YET!!!

    /**
     * WATCHLIST BAR
     */ //WILL DO LATER

    //private final By readMore

    //Regarding the dropdown menus to check if a certain part of a site exists, just use the URL? Will figure this out
    //at a later date. This applies to other applications as well.
    //Include whole modal elements for the tests that check if a modal closed successfully
    //Tests for color detection will be figured out during the time and as such, no elements have been identified (color)


    Actions execute = new Actions(driver);

    public SecurityOverviewPage(WebDriver driver) {
        super(driver);
    }


    /**
     *  EXISTS      //Create abstract class that contains all these.
     */


    public  boolean dropdownMenuExists()
    {
        pause(4000L);
        return doesElementExist(dropdownModal);
    }


    public boolean overviewPageExists()
    {
        pause(4000L);
        return doesElementExist(overviewModal);

    }

    public boolean ownershipPageExists()
    {
        pause(4000L);
        return doesElementExist(ownershipModal);
    }


    /**
     * GETTERS:
     */

    //HEADER\\
    public String getCompanyName() {
        return findElement(companyName).getText().replaceAll("\\p{P}", "");
    }

    public String getCompanyTicker() {
        return findElement(companyTicker).getAttribute("innerHTML").replaceAll("\\p{P}", "");
    }

    public String getIndustry_Exchange() {
        return findElement(industry_Exchange).getText().replaceAll("\\p{P}", "");
    }

    public String getStockQuote() {
        pause(4000L);
        return findElement(stockQuote).getText();

    }

    public String getChangeIconColor() {
        pause(4000L);
        String color = findElement(changeIcon).getCssValue("background-color");
        return color.replaceAll("[ rgba(),]", "");
    }

    public String getStockChange() {
        pause(4000L);
        return findElement(stockChange).getText();
    }

    public String getVolume() {
        pause(4000L);
        return findElement(volume).getText();
    }

    public String getAvgVolume() {
        pause(4000L);
        ;
        return findElement(avgVolume).getText();
    }

    public String getRecentEstimatesButtonNumber() { //Think of a better name...
        waitForLoadingScreen();
        return findElement(recentEstimatesBtn).getText();
    }

    public int getNumEstimatesResultsDisplayed() {
        return findElements(recentEstimatesResults).size();
    }

    public String getRecentNewsButtonNumber() {
        waitForLoadingScreen();
        return findElement(recentNewsBtn).getText();
    }

    public int getNumNewsResultsDisplayed() {
        return findElements(recentNewsResults).size();
    }

    public String getRecentEventsButtonNumber() {
        waitForLoadingScreen();
        return findElement(recentEventsBtn).getText();
    }

    public int getNumEventsResultsDisplayed() {
        return findElements(recentEventsResults).size();
    }

    public String getRecentTranscriptsButtonNumber() {
        waitForLoadingScreen();
        return findElement(recentTranscriptsBtn).getText();
    }

    public int getNumTranscriptsResultsDisplayed() {
        return findElements(recentTranscriptsResults).size();
    }

    //CHARTS\\

    public String getPrevCloseValue() {
        return findElement(prevCloseValue).getText().replaceAll("\\p{P}", "");
    }

    public String getOpenValue() {
        return findElement(openValue).getText().replaceAll("\\p{P}", "");
    }

    public String getIntradayLowValue1() //Not found in chart
    {
        return findElement(intradayLowValue1).getText().replaceAll("\\p{P}", "");
    }

    public String getIntradayLowValue2() {
        return findElement(intradayLowValue2).getText().replaceAll("\\p{P}", "");
    }

    public String getIntradayHighValue1() {
        return findElement(intradayHighValue1).getText().replaceAll("\\p{P}", "");
    }

    public String getIntradayHighValue2() {
        return findElement(intradayHighValue2).getText().replaceAll("\\p{P}", "");
    }

    public String getYearLowValue1() {
        return findElement(yearLowValue1).getText().replaceAll("\\p{P}", "");
    }

    public String getYearLowValue2() {
        return findElement(yearLowValue2).getText().replaceAll("\\p{P}", "");
    }

    public String getYearHighValue1() {
        return findElement(yearHighValue1).getText().replaceAll("\\p{P}", "");
    }

    public String getYearHighValue2() {
        return findElement(yearHighValue2).getText().replaceAll("\\p{P}", "");
    }

    public String getDailyVolumeValue() {
        return findElement(dailyVolumeValue).getText().replaceAll("\\p{P}", "");
    }

    public String getCurrencyType() {
        return findElement(currencyType).getText().replaceAll("\\p{P}", "");
    }

    //QUALITY RATING\\

    public String getGrowthValue() {
        return findElement(growthValue).getText().replaceAll("\\p{P}", "");
    }

    public String getIndexValue() {
        return findElement(indexValue).getText().replaceAll("\\p{P}", "");
    }

    public String getYieldValue() {
        return findElement(yieldValue).getText().replaceAll("\\p{P}", "");
    }

    public String getInvestorStyleOtherValue() {
        return findElement(investorStyleOtherValue).getText().replaceAll("\\p{P}", "");
    }


    public String getVeryLowValue() {
        return findElement(veryLowValue).getText().replaceAll("\\p{P}", "");
    }

    public String getLowValue() {
        return findElement(lowValue).getText().replaceAll("\\p{P}", "");
    }

    public String getMediumValue() {
        return findElement(mediumValue).getText().replaceAll("\\p{P}", "");
    }

    public String getTurnoverOtherValue() {
        return findElement(turnoverOtherValue).getText().replaceAll("\\p{P}", "");
    }

    //MINI CHARTS\\

    public String getHiValue() {
        return findElement(hiValue).getText().replaceAll("\\p{P}", "");
    }

    public String getLoValue() {
        return findElement(loValue).getText().replaceAll("\\p{P}", "");
    }

    public String getActualValue() {
        return findElement(actualValue).getText().replaceAll("\\p{P}", "");
    }

    public String getStockValue() {
        return findElement(stockValue).getText().replaceAll("\\p{P}", "");
    }

    public String getSentimentValue() {
        return findElement(sentimentValue).getText().replaceAll("\\p{P}", "");
    }

    public String getVolatilityValue() {
        return findElement(volatilityValue).getText().replaceAll("\\p{P}", "");
    }

    public String getActivismValue() {
        return findElement(activismValue).getText().replaceAll("\\p{P}", "");
    }


    /** A click used to exit a modal via inputing a web pages coordinates via offset of existing element.
     * This is used as modals may block/hide elements when they are up, so a reliable method is required to click.*/

    /**
     * CLICKERS
     */

    //RECALL, ALL TESTS ARE BASED ON OVERVIEW PAGE, SO JUST CHECKS IF A BUTTON TAKES YOU TO RIGHT PAGE BUT NO MORE.
    public SecurityOverviewPage clickDropdownLeftArrowOverview()  //when testing, do this first to make sure you are still in same page
    {
        findElement(dropdownLeftArrow).click();
        return this;
    }


    public SecurityOwnershipPage clickDropdownRightArrowOverview()     //Other versions of this on other pages
    {
        findElement(dropdownRightArrow).click();
        return new SecurityOwnershipPage(getDriver());
    }

    public SecurityOverviewPage clickViewDropdownMenu() {
        findElement(dropdownMenu).click();
        return this;
    }

    public SecurityOverviewPage clickDropdownOverview()//Perhaps put these methods into an interface for security?
    {
        findElement(dropdownOverview).click();
        return new SecurityOverviewPage(getDriver());
    }

    public SecurityOwnershipPage clickDropdownOwnership()
    {
          findElement(dropdownOwnership).click();
          return new SecurityOwnershipPage(getDriver());
    }

    public TradingRangePage clickDropdownTrading() {

        findElement(dropdownTrading).click();
        return new TradingRangePage(getDriver());
    }

    public SentimentPage clickDropdownSentiment() {

         findElement(dropdownSentiment).click();
         return new SentimentPage(getDriver());
    }

     public VolatilityPage clickDropdownVolatility() {

          findElement(dropdownVolatility).click();
          return new VolatilityPage(getDriver());
     }
     public ActivismPage clickDropdownActivism() {

          findElement(dropdownActivism).click();
          return new ActivismPage(getDriver());
     }

     public RelativePerformacePage clickDropdownPerformance() {

          findElement(dropdownPerformance).click();
          return new RelativePerformacePage(getDriver());
     }

     public SecurityEstimatesPage clickDropdownEstimates() {

          findElement(dropdownEstimates).click();
          return new SecurityEstimatesPage(getDriver());
     }

     public void clickRecentEstimatesButton(){
         findElement(recentEstimatesBtn).click();
     }

     public void clickRecentNewsButton(){
         
         findElement(recentNewsBtn).click();
     }

     public void clickRecentTranscriptsButton(){
         
         findElement(recentTranscriptsBtn).click();
     }

     public void clickRecentEventsButton(){
         
         findElement(recentEventsBtn).click();
     }

     public void offsetCompanyNameClick(int x, int y)

     {
        //Moves the mouse to an offset from the top-left corner of the companyName element.
        //Get chrome extension Mouse XY to find coordinates of a web-page
        //The companyName element has coordinates of about (80, 95) when in 100% zoom

        WebElement offsetElement = findElement(companyName);
        execute.moveToElement(offsetElement, x, y).click().perform();     //try a double click after
    }

    // Gets the stock price from the security details page
    public float getStockPrice() {
        return Float.parseFloat(findElement(stockQuote).getText());
    }
}
