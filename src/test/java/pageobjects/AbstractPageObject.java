package pageobjects;

import org.apache.commons.collections4.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.admin.companyPage.CompanyPage;
import pageobjects.admin.implementationPage.ImplementationPage;
import pageobjects.admin.intelligencePage.IntelligencePage;
import pageobjects.admin.profilesPage.ProfilesList;
import pageobjects.user.logActivity.LogActivityModal;
import pageobjects.user.sideNavBar.SideNavBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AbstractPageObject implements PageObject {

    public final WebDriver driver;

    public final WebDriverWait wait;

    private final Predicate<WebElement> displayedElementPredicate = new Predicate<WebElement>() {
        @Override
        public boolean evaluate(WebElement t) {
            return t.isDisplayed();
        }
    };

    // Side hamburger menu icon
    private final By sideNavIcon = By.cssSelector(".page-home .menu-btn"); //use for dashboard
    private final By hamburgerIcon = By.cssSelector(".navigation-toggler .x-button-icon"); //use for other pages

    private final By pageTitle = By.cssSelector(".q4-hero-banner .page-title");
    private final By otherPageTitle = By.cssSelector(".q4-hero-banner .page-title h1");

    // Admin page elements
    private final By adminPageTitle = By.cssSelector(".page-header .page-title .details h2");
    private final By loading = By.className("outer-spinner-container");
    private final By homePage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(1) > a > i");
    private final By companyPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(2) > a > i");
    private final By profilesPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(3) > a > i");
    private final By intelligencePage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(4) > a > i");
    private final By implementationPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(5) > a > i");
    private final By rolesPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(6) > a > i");
    private final By reportHeader = By.cssSelector(".page-header .page-title .details");


    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5L);
    }

    @Override
    public WebDriver getDriver() {
        return this.driver;
    }

    @Override
    public Predicate<WebElement> getDisplayedElementPredicate() {
        return this.displayedElementPredicate;
    }

    @Override
    public WebDriverWait getWait() {
        return this.wait;
    }

    // Check if subscription toggle is unchecked. If its unchecked, check it
    public void toggleOn(By selector) {
        String value = findElement(selector).getAttribute("class");
        if (value.equals("off ng-untouched ng-pristine ng-valid")) {
            findElement(selector).click();
        }
    }

    // Check if subscription toggle is check. If its checked, un-check it
    public void toggleOff(By selector) {
        String value = findElement(selector).getAttribute("class");
        System.out.println(value);
        if (value.equals("on ng-untouched ng-pristine ng-valid")) {
            findElement(selector).click();
        }
    }

    //use from dashboard
    public SideNavBar accessSideNav() {
        waitForLoadingScreen();
        findElement(sideNavIcon).click();

        return new SideNavBar(getDriver());
    }

    //use from other pages
    public SideNavBar accessSideNavFromPage() {
        findElement(hamburgerIcon).click();
        return new SideNavBar(getDriver());
    }

    public String getPageTitle() {
        pause(500L);
        return findElement(pageTitle).getText();
    }

    // Some pages have a different header
    public String getOtherPageTitle() {
        pause(500L);
        return findElement(otherPageTitle).getText();
    }

    public LogActivityModal pageRefresh() {
        pause(2000L);
        driver.navigate().refresh();

        return new LogActivityModal(getDriver());
    }

    public void goBackPages(int numPages) //Added this to allow functionality to go to a prev. page
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.history.go(-" + numPages + ")");
        pause(2000L);
    }

    public void waitForLoadingScreen() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loading));
    }

    public boolean elementsAreAlphaUpSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (elements.get(i+1).getText().compareTo/*IgnoreCase*/(elements.get(i).getText()) < 0){
                System.out.println("MIS-SORT: Ascending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreAlphaDownSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (elements.get(i+1).getText().compareTo/*IgnoreCase*/(elements.get(i).getText()) > 0){
                System.out.println("MIS-SORT: Descending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    /** Used for numerical values displayed on page. Treats '-' as having value of zero. */
    private double getNumFromText(String text){
        if (text.equals("-")){
            return 0;
        }
        else {
            return Double.parseDouble(text);
        }
    }

    public boolean elementsAreNumUpSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (getNumFromText(elements.get(i+1).getText()) < getNumFromText(elements.get(i).getText())){
                System.out.println("MIS-SORT: Ascending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreNumDownSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (getNumFromText(elements.get(i+1).getText()) > getNumFromText(elements.get(i).getText())){
                System.out.println("MIS-SORT: Descending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    private enum Turnover {
        None, Very_Low, Low, Medium, High, Very_High
    }

    private int turnoverTextToValue(String text){
        if (text.equals("-")){
            return Turnover.None.ordinal();
        }
        else {
            return Turnover.valueOf(text.replace(' ','_')).ordinal();
        }
    }

    public boolean elementsAreTurnoverUpSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (turnoverTextToValue(elements.get(i+1).getText()) < turnoverTextToValue(elements.get(i).getText())){
                System.out.println("MIS-SORT: Ascending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreTurnoverDownSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (turnoverTextToValue(elements.get(i+1).getText()) > turnoverTextToValue(elements.get(i).getText())){
                System.out.println("MIS-SORT: Descending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreDateUpSorted(List<WebElement> elements){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            try {
                if(dateFormat.parse(elements.get(i+1).getText()).before(dateFormat.parse(elements.get(i).getText()))){
                    System.out.println("MIS-SORT: Ascending: Date "+elements.get(i+1).getText()+" should not be after "+elements.get(i).getText());
                    sortedWell = false;
                }
            }catch (ParseException e){
                System.out.println("Error parsing date: "+elements.get(i+1).getText());
                return false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreDateDownSorted(List<WebElement> elements){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            try {
                if(dateFormat.parse(elements.get(i+1).getText()).after(dateFormat.parse(elements.get(i).getText()))){
                    System.out.println("MIS-SORT: Descending: Date "+elements.get(i+1).getText()+" should not be after "+elements.get(i).getText());
                    sortedWell = false;
                }
            }catch (ParseException e){
                System.out.println("Error parsing date: "+elements.get(i+1).getText());
                return false;
            }
        }
        return sortedWell;
    }

    // ADMIN METHODS \\

    public void switchToNewTab() {
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
    }

    public CompanyPage navigateToCompanyPage() {
        waitForLoadingScreen();
        findElement(companyPage).click();

        return new CompanyPage(getDriver());
    }

    public String getAdminPageTitle() {
        return findElement(adminPageTitle).getText();
    }

    public ProfilesList navigateToProfilesPage() {
        waitForLoadingScreen();
        findElement(profilesPage).click();
        waitForLoadingScreen();

        return new ProfilesList(driver);
    }

    public ImplementationPage navigateToImplementationPage() {
        waitForLoadingScreen();
        findElement(implementationPage).click();

        return new ImplementationPage(getDriver());
    }

    public IntelligencePage navigateToIntelligencePage() {
        waitForLoadingScreen();
        findElement(intelligencePage).click();

        return new IntelligencePage(getDriver());
    }

    public String getReportHeader() {
        waitForLoadingScreen();
        return findElement(reportHeader).getText();
    }
}
