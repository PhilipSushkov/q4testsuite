package pageobjects.user.targeting;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.fundPage.FundPage;
import pageobjects.user.institutionPage.InstitutionPage;

import java.util.List;

/**
 * Created by patrickp on 2016-09-19.
 */
public class TargetingPage extends AbstractPageObject {

    //Many selectors are different from the Saved Searches tab vs the Saved targets tab

    private final By newSearchButton = By.cssSelector(".btn.x-button.x-unsized:not(.btn-block)");
    private final By showSearches = By.cssSelector(".x-tabbar-inner div:first-child");
    private final By searchNameSelectors = By.cssSelector(".targeting-landing-list .x-dataview-item .name");
    private final By searchTableRow = By.xpath("//div[contains(@class,'x-dataview-item')]");
    private final By searchNameDivSelectors = By.cssSelector(".targeting-landing-list .x-dataview-item");

    private final By checkbox = By.cssSelector(".checkbox-mask");
    private final By trashIcon = By.xpath("//div[not(contains(@class,'disabled')) and ./span[contains(@class,'q4i-trashbin-4pt')]]");
    //private final By trashIcon = By.cssSelector(".q4i-trashbin-4pt");
    private final By cancelDelete = By.xpath("//div[contains(@class,'x-msgbox')]//div[./span[contains(text(),'No')]]");
    private final By confirmDelete = By.xpath("//div[contains(@class,'x-msgbox')]//div[./span[contains(text(),'Yes')]]");
    private final By doneButton = By.cssSelector(".done .x-button-label");

    private final By searchesColumnHeader = By.cssSelector(".targeting-landing-list .list-header");
    private final By nameColumnSearches = By.xpath("//div[contains(@class,'x-button')]//span[contains(text(),'Name')]");
    private final By createdColumnSearches = By.xpath("//div[contains(@class,'x-button')]//span[contains(text(),'Created')]");
    private final By updatedColumnSearches = By.xpath("//div[contains(@class,'x-button')]//span[contains(text(),'Updated')]");
    private final By searchCreatedDate = By.cssSelector(".targeting-landing-list .column.centered.created");
    private final By searchUpdatedDate = By.cssSelector(".targeting-landing-list .x-dataview-item .column.centered.updated");
    private final By searchShowMoreButton = By.cssSelector(".load-more .x-button");
    private final By searchSearchInput = By.xpath("//*[contains(@class,'toolbar-panel')]//input");

    //targeting tab buttons
    private final By showTargets = By.cssSelector(".x-tabbar-inner div:last-child");
    private final By showInstitutions = By.xpath("//div[contains(@class,'range-tabs-inner')]/div[span/text()='Institutions']");
    private final By showFunds = By.xpath("//div[contains(@class,'range-tabs-inner')]/div[span/text()='Funds']");
    private final By showContacts = By.xpath("//div[contains(@class,'range-tabs-inner')]/div[span/text()='Contacts']");
    private final By firstEntitySelector = By.cssSelector(".targeting-landing-list .x-dataview-item:first-child");
    private final By firstEntityNameSelector = By.cssSelector(".targeting-landing-list .x-dataview-item:first-child .name");
    //private final By firstEntityNameSelector = By.cssSelector(".targeting-grid-item-first.x-has-height div:first-child .x-grid-cell-inner");
    private final By entityNameTargetPage = By.xpath("//div[contains(@class,'targeting-name')]");
    private final By entityName = By.cssSelector(".targeting-landing-list .x-dataview-item .name");
    private final By entityTargetButton = By.className("target");
    private final By entityTargetNumber = By.xpath("//div[@class='details'][string-length(text()) > 0]");
    private final By targetsNameColumnHeader = By.xpath("//div[contains(@class,'x-button')]//span[contains(text(),'Name')]");
    private final By targetsLocationColumnHeader = By.xpath("//div[contains(@class,'x-button')]//span[contains(text(),'Location')]");
    private final By entityLocation = By.xpath("//div[contains(@class,'location-value')]");
    private final By searchResults = By.cssSelector(".targeting-landing-list");
    private final By targetShowMoreButton = By.xpath("//div[contains(@class,'targeting-landing-list') and not(contains(@class,'x-hidden-display'))]//span[contains(@class,'q4i-arrow-down-2pt')]");

    public TargetingPage(WebDriver driver) {
        super(driver);
    }


    public NewSearchPage newSearch(){
        waitForLoadingScreen();
        waitForElementToBeClickable(newSearchButton);
        findElement(newSearchButton).click();

        return new NewSearchPage(getDriver());

    }

    // returns the position (starting from 0) that the search name appears; returns -1 if not displayed
    public Boolean searchExists(String searchName){
        waitForElement(showSearches);
        searchForSearch(searchName);

        List<WebElement> searchNames = findVisibleElements(searchNameSelectors);

        for (int i=0; i<searchNames.size(); i++){
            if (searchNames.get(i).getText().equals(searchName)){
                return true;
            }
        }
        return false;
    }

    public TargetingPage searchForSearch(String searchName){
        waitForLoadingScreen();
        waitForAnyElementToAppear(searchSearchInput);
        findVisibleElement(searchSearchInput).click();
        findVisibleElement(searchSearchInput).clear();
        findVisibleElement(searchSearchInput).sendKeys(searchName);
        waitForElementToRest(searchTableRow, 1000L);
        waitForLoadingScreen();
        return this;
    }

    public WebElement returnSearch(String searchName){
        waitForElement(showSearches);
        waitForLoadingScreen();
        searchForSearch(searchName);

        List<WebElement> searchNames = findVisibleElements(searchTableRow);
        for(WebElement row: searchNames){
            if(row.getText().contains(searchName)){
                return row;
            }
        }
        return null;
    }

    public TargetingPage deleteSearchAbort(WebElement search){
        waitForElement(checkbox);
        search.findElement(checkbox).click();
        waitForElement(trashIcon);
        findElement(trashIcon).click();
        waitForElementToAppear(cancelDelete);
        findElement(cancelDelete).click();
        return this;
    }
    // parameter index is the position (starting from 0) that the search name appears
    public EditSearchPage editSearch(WebElement search){
        search.click();
        return new EditSearchPage(getDriver());
    }


    public TargetingPage deleteSearch(WebElement search){
        waitForLoadingScreen();

        try{
             search.findElement(By.xpath(".//div[contains(@class,' selected')]"));
            }
        catch(Exception e) {
            search.findElement(checkbox).click();
        }
        waitForElementToBeClickable(trashIcon).click();
        waitForElementToAppear(confirmDelete);
        findElement(confirmDelete).click();

        waitForLoadingScreen();
        return this;
    }

    public String getCreatedDate(){
        waitForAnyElementToAppear(searchCreatedDate);
        return findVisibleElement(firstEntitySelector).findElement(searchCreatedDate).getText();
    }

    public String getUpdatedDate(){
        waitForAnyElementToAppear(searchUpdatedDate);
        return findVisibleElement(firstEntitySelector).findElement(searchUpdatedDate).getText();
    }

    public boolean searchesCanBeSortedByName() {
        waitForElementToAppear(searchesColumnHeader);
        pause(2000);

        // sorting by name ascending
        findVisibleElement(nameColumnSearches).click();
        pause(500);
        waitForLoadingScreen();
        if (!elementsAreAlphaUpSortedIgnoreCase(findElements(searchNameSelectors))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }

        // sorting by name descending
        findVisibleElement(nameColumnSearches).click();
        pause(500);
        waitForLoadingScreen();
        if (!elementsAreAlphaDownSortedIgnoreCase(findElements(searchNameSelectors))) {
            System.out.println("SORT ERROR: Names are not in descending order.");
            return false;
        }
        return true;
    }


    public boolean searchesCanBeSortedByDate() {
        waitForElementToAppear(searchesColumnHeader);
        pause(2000);
        // sorting by created date descending
        findVisibleElement(createdColumnSearches).click();
        pause(500);
        waitForLoadingScreen();
        if (!elementsAreDateDownSorted(findElements(searchCreatedDate))) {
            System.out.println("SORT ERROR: Created dates are not in descending order.");
            return false;
        }

        // sorting by created date ascending
        findVisibleElement(createdColumnSearches).click();
        pause(500);
        waitForLoadingScreen();
        if (!elementsAreDateUpSorted(findElements(searchCreatedDate))) {
            System.out.println("SORT ERROR: Created dates are not in ascending order.");
            return false;
        }
        return true;
    }

    public boolean searchesCanBeSortedByUpdatedDate() {
        waitForElementToAppear(searchesColumnHeader);
        // sorting by last updated date ascending
        waitForElementToBeClickable(updatedColumnSearches).click();
        waitForLoadingScreen();
        waitForElementToRest(searchResults, 500L);
        if (!elementsAreDateUpSorted(findElements(searchUpdatedDate))){
            System.out.println("SORT ERROR: Last updated dates are not in ascending order.");
            return false;
        }

        // sorting by last updated date descending
        findVisibleElement(updatedColumnSearches).click();
        waitForLoadingScreen();
        waitForElementToRest(searchResults, 500L);
        if (!elementsAreDateDownSorted(findElements(searchUpdatedDate))){
            System.out.println("SORT ERROR: Last updated dates are not in descending order.");
            return false;
        }

        return true;
    }

    // returns the position (starting from 0) that the institution appears; returns -1 if not displayed
    public int findInstitutionIndex(String name){
        waitForElement(showTargets);
        findVisibleElement(showTargets).click();
        waitForLoadingScreen();
        findVisibleElement(showInstitutions).click();
        waitForLoadingScreen();

        List<WebElement> institutionNames = findVisibleElements(entityName);

        for (int i=0; i<institutionNames.size(); i++){
            if (institutionNames.get(i).getText().contains(name)){
                return i;
            }
        }
        return -1;
    }

    // returns the position (starting from 0) that the contact appears; returns -1 if not displayed
    public int findContactIndex(String name){
        waitForElement(showTargets);
        findVisibleElement(showTargets).click();
        waitForLoadingScreen();
        findVisibleElement(showContacts).click();
        waitForLoadingScreen();

        List<WebElement> contactNames = findVisibleElements(entityName);

        for (int i=0; i<contactNames.size(); i++){
            if (contactNames.get(i).getText().contains(name)){
                return i;
            }
        }
        return -1;
    }

    public void untargetInstitution(int index){
        findVisibleElements(entityTargetButton).get(index).click();
    }

    public void untargetContact(int index){
        findVisibleElements(entityTargetButton).get(index).click();
    }

    public String getFirstInstitution(){
        waitForElementToBeClickable(showTargets).click();
        waitForElementToRest(showInstitutions, 200L);
        waitForElementToBeClickable(showInstitutions).click();
        pause(2000);
        return findVisibleElement(firstEntityNameSelector).getText();
    }

    public String getFirstFund(){
        waitForElementToBeClickable(showTargets).click();
        waitForElementToRest(showFunds, 200L);
        waitForElementToBeClickable(showFunds).click();
        pause(2000);
        return findVisibleElement(firstEntityNameSelector).getText();
    }

    public String getFirstContact(){
        waitForElementToBeClickable(showTargets).click();
        waitForElementToRest(showContacts, 200L);
        waitForElementToBeClickable(showContacts).click();
        pause(2000);
        return findVisibleElement(firstEntityNameSelector).getText();
    }

    public InstitutionPage openFirstInstitution(){

        findVisibleElement(firstEntitySelector).click();
        return new InstitutionPage(getDriver());
    }

    public FundPage openFirstFund(){
        findVisibleElement(firstEntitySelector).click();
        return new FundPage(getDriver());
    }

    public ContactDetailsPage openFirstContact(){
        findVisibleElement(firstEntitySelector).click();
        return new ContactDetailsPage(getDriver());
    }

    public InstitutionPage goToInstitutionURL(String URL){
        pause(2000);
        driver.get(URL);
        return new InstitutionPage(getDriver());
    }

    public ContactDetailsPage goToContactURL(String URL){
        pause(2000);
        driver.get(URL);
        return new ContactDetailsPage(getDriver());
    }

    public boolean allTargetsCanBeSorted(){
        waitForAnyElementToAppear(showTargets);
        findVisibleElement(showTargets).click();
        pause(2000);

        //clicking Show More - See DESKTOP-8189
        return checkSorted(1000);
    }

    public boolean institutionsCanBeSorted(){
        int beforeShowMore=0;
        waitForAnyElementToAppear(showTargets);
        findVisibleElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showInstitutions));
        findElement(showInstitutions).click();
        pause(2000);

        //clicking Show More - See DESKTOP-8189
        return checkSorted(1000);
    }

    public boolean fundsCanBeSorted(){
        int beforeShowMore = 0;
        waitForAnyElementToAppear(showTargets);
        findVisibleElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showFunds));
        findElement(showFunds).click();
        pause(2000);

        return checkSorted(1000);
    }

    public boolean contactsCanBeSorted(){
        waitForAnyElementToAppear(showTargets);
        findVisibleElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showContacts));
        findElement(showContacts).click();
        pause(2000);

        return checkSorted(1000);

    }

    public boolean checkSorted(int waitTime){
        int beforeShowMore = 0;
        List<WebElement> beforeSort = findElements(entityTargetNumber);
        beforeShowMore = beforeSort.size();

        if (beforeShowMore < 10) {
            try {
                findElement(targetShowMoreButton).click();
                waitForLoadingScreen();
            } catch (ElementNotVisibleException e) {
                System.out.println("There are less than 10 items therefore there is no Show More button");
                System.out.println("Expection: " + e);
            }
        }

        // sorting by name ascending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(waitTime);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityNameTargetPage))){
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }

        // sorting by name descending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(waitTime);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityNameTargetPage))){
            System.out.println("SORT ERROR: Names are not in descending order.");
            return false;
        }

        // sorting by location ascending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(waitTime);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in ascending order.");
            return false;
        }

        // sorting by location descending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(waitTime);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in descending order.");
            return false;
        }

        int afterNum = findElements(entityTargetNumber).size();
        //checking to make sure the Show More still exists after sorting
        if(!(beforeShowMore == afterNum)){
            System.out.println("Show More did not stay after sort.");
            return false;
        }

        return true;
    }

    public String getSearchResults() {
        waitForLoadingScreen();
        return findVisibleElement(searchResults).getText();
    }

    public TargetingPage selectTargetsTab() {
        waitForElement(showTargets);
        findVisibleElement(showTargets).click();
        waitForLoadingScreen();
        return this;
    }
}
