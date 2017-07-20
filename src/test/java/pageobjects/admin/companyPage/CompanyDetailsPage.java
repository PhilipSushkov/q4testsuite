package pageobjects.admin.companyPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompanyDetailsPage extends CompanyList {

    private final By addButton = By.cssSelector(".square-button.add");
    private final By companySearchField = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-autocomplete.auto-complete-search .ui-inputtext");
    private final By saveButton = By.xpath("/html/body/q4-app/div/div/q4-organization-details/q4-organization-peers/p-dialog[1]/div/div[2]/q4-peer-create/div/button[2]");
    private final By companyNameSaveButton = By.xpath("/html/body/q4-app/div/div/q4-organization-details/p-dialog[2]/div/div[2]/q4-organization-details-edit/div/button");
    private final By peerTab = By.xpath("//div[contains(@class,'tab')][span[contains(text(),'Peers')]]");
    private final By searchResult = By.cssSelector("body > q4-app > div > div > q4-organization-details > q4-organization-peers > p-dialog:nth-child(3) > div > div.ui-dialog-content.ui-widget-content > q4-peer-create > p-autocomplete > span > div");

    private final By editCompanyButton = By.xpath("/html/body/q4-app/div/div/q4-organization-details/header/div/div[2]/button[1]");
    private final By companyNameField = By.xpath("//p-dialog[contains(@header,'Edit Company Name')]//input[contains(@placeholder,'Name')]");
    private final By companyEditField = By.name("company_name");
    private final By tickerTab = By.cssSelector(".toolbar-manager .tabs .tab:nth-child(4)");
    private final By mailingListTab = By.cssSelector(".toolbar-manager .tabs .tab:nth-child(6)");

    // Ticker page
    private final By newTickerIcon = By.cssSelector(".sub-toolbar .action-buttons .add");
    private final By editTickerSearchField = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-autocomplete.auto-complete-search .ui-inputtext");
    private final By firstTicker = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-autocomplete .ui-autocomplete-panel .ui-autocomplete-list .ui-autocomplete-list-item:nth-child(1)");
    private final By tickerSave = By.cssSelector(".button-yellow");
    private final By removeTickerIcon = By.cssSelector(".q4-list .action-buttons .remove");
    private final By confirmButton = By.cssSelector("body > q4-app > div > div > q4-organization-details > q4-organization-tickers > p-datatable > div > div > table > tbody > tr:nth-child(1) > td.action-buttons > span > ng-component > p-dialog > div > div.ui-dialog-content.ui-widget-content > div > button.button.button-red");
    private final By modalCancelButton = By.cssSelector("body > q4-app > div > div > q4-organization-details > q4-organization-tickers > p-dialog:nth-child(3) > div > div.ui-dialog-content.ui-widget-content > q4-ticker-create > div > button.button.button-no-background");

    // Mailing List page
    private final By morningCoffeeReportRow = By.xpath("//td[span[contains(text(),'Morning Coffee Report')]]");

    //Mailing List modal
    private final By addEmailField = By.xpath("//q4-organization-mailing-list//input[@placeholder='Add Emails']");
    private final By closeEditMailinglList = By.xpath("//q4-organization-mailing-list//a[@role='button']");
    private final By saveMailingList = By.xpath("//q4-organization-mailing-list//div[@class='buttons']/button[text()='Save']");
    private final By cancelMailingList =By.xpath("//q4-organization-mailing-list//div[@class='buttons']/button[text()='Cancel']");




    public CompanyDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean canClickPeerTab(){
        waitForLoadingScreen();
       try {
           wait.until(ExpectedConditions.elementToBeClickable(peerTab));
           return true;
       }
       catch(Exception e){
           return false;
        }
    }
    public CompanyDetailsPage addPeer(String company) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        findElement(addButton).click();
        findElement(companySearchField).sendKeys(company);
        waitForElementToAppear(searchResult);
        findElement(searchResult).click();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        findElement(saveButton).click();

        return this;
    }

    public CompanyDetailsPage editCompanyName(String newName) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(editCompanyButton));
        findElement(editCompanyButton).click();
        findElement(companyNameField).clear();
        findElement(companyNameField).sendKeys(newName);
        findElement(companyNameSaveButton).click();

        return this;
    }

    public CompanyDetailsPage addEditPeer(String name, String newName) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        findElement(addButton).click();
        findElement(companySearchField).sendKeys(name);
        waitForElementToAppear(searchResult);
        findElement(searchResult).click();
        findElement(companyEditField).clear();
        findElement(companyEditField).sendKeys(newName);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        findElement(saveButton).click();

        return this;
    }

    public CompanyDetailsPage selectTickerTab() {
        waitForLoadingScreen();
        findElement(tickerTab).click();
        waitForLoadingScreen();

        return this;
    }

    public CompanyDetailsPage selectMailingListTab() {
        waitForLoadingScreen();
        findElement(mailingListTab).click();
        waitForLoadingScreen();

        return this;
    }



    public CompanyList addTicker(String newTicker) {
        findElement(newTickerIcon).click();
        findElement(editTickerSearchField).click();
        findElement(editTickerSearchField).sendKeys(newTicker);
        waitForElementToAppear(firstTicker);
        findElement(firstTicker).click();
        findVisibleElement(tickerSave).click();

        return this;
    }

    public CompanyDetailsPage clickAddTickerButton() {
        findElement(newTickerIcon).click();

        return this;
    }

    public CompanyDetailsPage removeTicker() {
        findElement(removeTickerIcon).click();
        waitForElementToAppear(confirmButton);
        findVisibleElement(confirmButton).click();

        return this;
    }

    public CompanyDetailsPage dismissAddTickerModal() {
        findElement(modalCancelButton).click();
        waitForLoadingScreen();

        return this;
    }


    //Email List Methods

    public CompanyDetailsPage clickMorningCoffeeReport(){
        findElement(morningCoffeeReportRow).click();
        waitForLoadingScreen();
        return this;
    }

    public CompanyDetailsPage enterEmail(String email){
        findElement(addEmailField).sendKeys(email);
        findElement(addEmailField).sendKeys(Keys.ENTER);
        return this;
    }

    public CompanyDetailsPage closeEditMailingList(){
        findElement(closeEditMailinglList).click();
        waitForLoadingScreen();
        return this;
    }
    public CompanyDetailsPage saveMailingList(){
        findElement(saveMailingList).click();
        waitForLoadingScreen();
        return this;
    }
    public CompanyDetailsPage cancelMailingList(){
        findElement(cancelMailingList).click();
        waitForLoadingScreen();
        return this;
    }


}
