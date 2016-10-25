package pageobjects.user.targeting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.contactPage.ContactPage;
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
    private final By showTargets = By.cssSelector(".x-tabbar-inner div:last-child");
    private final By showInstitutions = By.xpath("//div[contains(@class,'range-tabs-inner')]/div[span/text()='Institutions']");
    private final By showFunds = By.xpath("//div[contains(@class,'range-tabs-inner')]/div[span/text()='Funds']");
    private final By showContacts = By.xpath("//div[contains(@class,'range-tabs-inner')]/div[span/text()='Contacts']");
    private final By firstEntitySelector = By.cssSelector(".targeting-grid-item-first.x-has-height");
    private final By firstEntityNameSelector = By.cssSelector(".targeting-grid-item-first.x-has-height div:first-child .x-grid-cell-inner");


    public TargetingPage(WebDriver driver) {
        super(driver);
    }


    public NewSearchPage newSearch(){
        waitForElement(newSearchButton);
        findElement(newSearchButton).click();

        return new NewSearchPage(getDriver());

    }

    public int findSearchNameIndex(String searchName){
        waitForElement(showSearches);
        List<WebElement> searchNames = findElements(searchNameSelectors);

        for (int i=0; i<searchNames.size(); i++){
            if (searchNames.get(i).getText().equals(searchName)){
                return i;
            }
        }
        return -1;
    }

    public EditSearchPage editSearch(int index){
        List<WebElement> searchNameDivs = findElements(searchNameDivSelectors);
        searchNameDivs.get(index).click();

        return new EditSearchPage(getDriver());
    }

    public String getFirstInstitution(){
        findElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showInstitutions));
        findElement(showInstitutions).click();
        pause(2000);
        List<WebElement> firstEntityName = findElements(firstEntityNameSelector);
        for (int i=0; i<firstEntityName.size(); i++){
            if (firstEntityName.get(i).isDisplayed()){
                return firstEntityName.get(i).getText();
            }
        }
        return "";
    }

    public String getFirstFund(){
        findElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showFunds));
        findElement(showFunds).click();
        pause(2000);
        List<WebElement> firstEntityName = findElements(firstEntityNameSelector);
        for (int i=0; i<firstEntityName.size(); i++){
            if (firstEntityName.get(i).isDisplayed()){
                return firstEntityName.get(i).getText();
            }
        }
        return "";
    }

    public String getFirstContact(){
        findElement(showTargets).click();
        pause(2000);
        wait.until(ExpectedConditions.elementToBeClickable(showContacts));
        findElement(showContacts).click();
        pause(2000);
        List<WebElement> firstEntityName = findElements(firstEntityNameSelector);
        for (int i=0; i<firstEntityName.size(); i++){
            if (firstEntityName.get(i).isDisplayed()){
                return firstEntityName.get(i).getText();
            }
        }
        return "";
    }

    public InstitutionPage openFirstInstitution(){
        List<WebElement> firstEntity = findElements(firstEntitySelector);
        for (int i=0; i<firstEntity.size(); i++){
            if (firstEntity.get(i).isDisplayed()){
                firstEntity.get(i).click();
                break;
            }
        }
        return new InstitutionPage(getDriver());
    }

    public FundPage openFirstFund(){
        List<WebElement> firstEntity = findElements(firstEntitySelector);
        for (int i=0; i<firstEntity.size(); i++){
            if (firstEntity.get(i).isDisplayed()){
                firstEntity.get(i).click();
                break;
            }
        }
        return new FundPage(getDriver());
    }

    public ContactDetailsPage openFirstContact(){
        List<WebElement> firstEntity = findElements(firstEntitySelector);
        for (int i=0; i<firstEntity.size(); i++){
            if (firstEntity.get(i).isDisplayed()){
                firstEntity.get(i).click();
                break;
            }
        }
        return new ContactDetailsPage(getDriver());
    }

}
