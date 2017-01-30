package pageobjects.user.targeting;

import org.openqa.selenium.By;
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

    private final By newSearchButton = By.cssSelector(".q4-hero-banner .x-dock .action-button");
    private final By showSearches = By.cssSelector(".x-tabbar-inner div:first-child");
    private final By searchNameSelectors = By.cssSelector(".x-grid-row .x-grid-cell:first-child .x-grid-cell-inner");
    private final By searchNameDivSelectors = By.cssSelector(".x-grid-row");
    private final By editButton = By.cssSelector(".edit .x-button-label");
    private final By redButton = By.cssSelector(".delete-button .q4i-minus-4pt");
    private final By cancelDelete = By.cssSelector(".targeting-action-toolbar .x-button:first-child");
    private final By confirmDelete = By.cssSelector(".targeting-action-toolbar .x-button.delete");
    private final By doneButton = By.cssSelector(".done .x-button-label");
    private final By searchesColumnHeader = By.cssSelector(".x-grid-header-container-inner .x-grid-column");
    private final By searchCreatedDate = By.cssSelector(".x-grid-row .x-grid-cell:nth-child(2)");
    private final By searchUpdatedDate = By.cssSelector(".x-grid-row .x-grid-cell:nth-child(3)");
    private final By showMoreButton = By.cssSelector(".load-more .x-button");

    private final By showTargets = By.cssSelector(".x-tabbar-inner div:last-child");
    private final By showInstitutions = By.xpath("//div[contains(@class,'range-tabs-inner')]/div[span/text()='Institutions']");
    private final By showFunds = By.xpath("//div[contains(@class,'range-tabs-inner')]/div[span/text()='Funds']");
    private final By showContacts = By.xpath("//div[contains(@class,'range-tabs-inner')]/div[span/text()='Contacts']");
    private final By firstEntitySelector = By.cssSelector(".targeting-grid-item-first.x-has-height");
    private final By firstEntityNameSelector = By.cssSelector(".targeting-grid-item-first.x-has-height div:first-child .x-grid-cell-inner");
    private final By entityName = By.cssSelector(".x-grid-row.q4-grid.x-has-height div:first-child .x-grid-cell-inner");
    private final By entityTargetButton = By.className("target");
    private final By targetsNameColumnHeader = By.cssSelector(".x-grid-column:first-child");
    private final By targetsLocationColumnHeader = By.cssSelector(".x-grid-column:nth-child(2)");
    private final By entityLocation = By.cssSelector(".x-grid-row.q4-grid.x-has-height div:nth-child(2) .x-grid-cell-inner");


    public TargetingPage(WebDriver driver) {
        super(driver);
    }


    public NewSearchPage newSearch(){
        waitForElement(newSearchButton);
        findElement(newSearchButton).click();

        return new NewSearchPage(getDriver());

    }

    private void showMoreSavedSearches(){
        int numSearches = findElements(searchNameSelectors).size();
        findVisibleElement(showMoreButton).click();
        for (int i=0; i<100; i++){
            if (findElements(searchNameSelectors).size()>numSearches){
                return;
            }
            pause(100);
        }
    }

    // returns the position (starting from 0) that the search name appears; returns -1 if not displayed
    public int findSearchNameIndex(String searchName){
        waitForElement(showSearches);
        pause(2000);
        while (findVisibleElements(showMoreButton).size()>0){
            showMoreSavedSearches();
        }
        List<WebElement> searchNames = findVisibleElements(searchNameSelectors);

        for (int i=0; i<searchNames.size(); i++){
            if (searchNames.get(i).getText().equals(searchName)){
                return i;
            }
        }
        return -1;
    }

    // parameter index is the position (starting from 0) that the search name appears
    public EditSearchPage editSearch(int index){
        findVisibleElements(searchNameDivSelectors).get(index).click();
        return new EditSearchPage(getDriver());
    }

    public void deleteSearchAbort(int index){
        waitForLoadingScreen();
        waitForElement(editButton);
        findVisibleElement(editButton).click();
        List<WebElement> redButtons = findVisibleElements(redButton);
        redButtons.get(index).click();
        waitForElementToAppear(cancelDelete);
        findElement(cancelDelete).click();
        waitForElementToDissapear(cancelDelete);
        findElement(doneButton).click();
    }

    public void deleteSearch(int index){
        waitForElement(editButton);
        findVisibleElement(editButton).click();
        waitForElementToAppear(redButton);
        List<WebElement> redButtons = findElements(redButton);
        redButtons.get(index).click();
        waitForElementToAppear(confirmDelete);
        findElement(confirmDelete).click();
        waitForElementToDissapear(confirmDelete);
        findElement(doneButton).click();
    }

    public String getCreatedDate(int index){
        return findVisibleElements(searchCreatedDate).get(index).getText();
    }

    public String getUpdatedDate(int index){
        return findVisibleElements(searchUpdatedDate).get(index).getText();
    }

    public boolean searchesCanBeSorted(){
        waitForElementToAppear(searchesColumnHeader);
        pause(2000);

        // sorting by name ascending
        findElements(searchesColumnHeader).get(0).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findElements(searchNameSelectors).subList(0,10))){
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }

        // sorting by name descending
        findElements(searchesColumnHeader).get(0).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findElements(searchNameSelectors).subList(0,10))){
            System.out.println("SORT ERROR: Names are not in descending order.");
            return false;
        }

        // sorting by created date ascending
        findElements(searchesColumnHeader).get(1).click();
        pause(300);
        if (!elementsAreDateUpSorted(findElements(searchCreatedDate).subList(0,10))){
            System.out.println("SORT ERROR: Created dates are not in ascending order.");
            return false;
        }

        // sorting by created date descending
        findElements(searchesColumnHeader).get(1).click();
        pause(300);
        if (!elementsAreDateDownSorted(findElements(searchCreatedDate).subList(0,10))){
            System.out.println("SORT ERROR: Created dates are not in descending order.");
            return false;
        }

        // sorting by last updated date ascending
        findElements(searchesColumnHeader).get(2).click();
        pause(300);
        if (!elementsAreDateUpSorted(findElements(searchUpdatedDate).subList(0,10))){
            System.out.println("SORT ERROR: Last updated dates are not in ascending order.");
            return false;
        }

        // sorting by last updated date descending
        findElements(searchesColumnHeader).get(2).click();
        pause(300);
        if (!elementsAreDateDownSorted(findElements(searchUpdatedDate).subList(0,10))){
            System.out.println("SORT ERROR: Last updated dates are not in descending order.");
            return false;
        }

        return true;
    }

    // returns the position (starting from 0) that the institution appears; returns -1 if not displayed
    public int findInstitutionIndex(String name){
        waitForElement(showTargets);
        findVisibleElement(showTargets).click();
        pause(2000);
        findVisibleElement(showInstitutions).click();
        pause(2000);
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
        pause(2000);
        findVisibleElement(showContacts).click();
        pause(3000);
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
        findElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showInstitutions));
        findElement(showInstitutions).click();
        pause(2000);
        return findVisibleElement(firstEntityNameSelector).getText();
    }

    public String getFirstFund(){
        findElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showFunds));
        findElement(showFunds).click();
        pause(2000);
        return findVisibleElement(firstEntityNameSelector).getText();
    }

    public String getFirstContact(){
        findElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showContacts));
        findElement(showContacts).click();
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
        findVisibleElement(showTargets).click();
        pause(2000);

        // sorting by name ascending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityName))){
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }

        // sorting by name descending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityName))){
            System.out.println("SORT ERROR: Names are not in descending order.");
            return false;
        }

        // sorting by location ascending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in ascending order.");
            return false;
        }

        // sorting by location descending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in descending order.");
            return false;
        }

        return true;
    }

    public boolean institutionsCanBeSorted(){
        findVisibleElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showInstitutions));
        findElement(showInstitutions).click();
        pause(2000);

        // sorting by name ascending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityName))){
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }

        // sorting by name descending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityName))){
            System.out.println("SORT ERROR: Names are not in descending order.");
            return false;
        }

        // sorting by location ascending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in ascending order.");
            return false;
        }

        // sorting by location descending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in descending order.");
            return false;
        }

        return true;
    }

    public boolean fundsCanBeSorted(){
        findVisibleElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showFunds));
        findElement(showFunds).click();
        pause(2000);

        // sorting by name ascending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityName))){
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }

        // sorting by name descending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityName))){
            System.out.println("SORT ERROR: Names are not in descending order.");
            return false;
        }

        // sorting by location ascending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in ascending order.");
            return false;
        }

        // sorting by location descending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in descending order.");
            return false;
        }

        return true;
    }

    public boolean contactsCanBeSorted(){
        findVisibleElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showContacts));
        findElement(showContacts).click();
        pause(2000);

        // sorting by name ascending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityName))){
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }

        // sorting by name descending
        findVisibleElement(targetsNameColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityName))){
            System.out.println("SORT ERROR: Names are not in descending order.");
            return false;
        }

        // sorting by location ascending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in ascending order.");
            return false;
        }

        // sorting by location descending
        findVisibleElement(targetsLocationColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(entityLocation))){
            System.out.println("SORT ERROR: Locations are not in descending order.");
            return false;
        }

        return true;
    }

}
