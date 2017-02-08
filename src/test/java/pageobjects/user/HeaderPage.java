package pageobjects.user;

import org.openqa.selenium.*;
import pageobjects.PageObject;
import pageobjects.user.securityPage.SecurityOverviewPage;

/**
 * Created by sarahr on 2/1/2017.
 */
public interface HeaderPage extends PageObject{

    //selectors

    //Header buttons
    By searchBar = By.xpath("//div[contains(@class,'global-header')]//input[contains(@type,'search')]");
    By securityButton = By.xpath("//div[contains(@class,'global-header')]//div[contains(@class,'profile-company')]");
    By chatButton = By.xpath("//div[contains(@class,'chat-button')]");
    By profileButton = By.xpath("//div[contains(@class,'profile') and contains(@class,'x-paint-monitored')]");

    //When Profile is open, these appear
    By releaseNotesButton = By.xpath("//span[contains(@class,'item') and contains(text(),'Release Notes')]");
    By leaveFeedbackButton = By.xpath("//span[contains(@class,'item') and contains(text(),'Feedback')]");
    By changePasswordButton = By.xpath("//span[contains(@class,'item') and contains(text(),'Password')]");
    By logoutButton = By.xpath("//span[contains(@class,'item') and contains(text(),'Logout')]");

    By logoutConfirmation = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'Yes')]");
    By logoutRejection = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'No')]");

    //Search results
    By securityResult = By.xpath("//div[contains(@class,'list-header') and contains(@class,'security')]/../../following-sibling::div[1]");
    By institutionResult = By.xpath("//div[contains(@class,'list-header') and contains(@class,'institution')]/../../following-sibling::div[1]");
    By contactResult = By.xpath("//div[contains(@class,'list-header') and contains(@class,'contact')]/../../following-sibling::div[1]");
    By fundResult = By.xpath("//div[contains(@class,'list-header') and contains(@class,'fund')]/../../following-sibling::div[1]");



    default HeaderPage headerSearch(String searchTerm){
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        return this;
    }

    default HeaderPage contactSearch(String searchTerm){
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        findElement(contactResult).click();

        return this;
    }

    default HeaderPage securitySearch(String searchTerm){
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        findElement(securityResult).click();

        return this;
    }

    default HeaderPage institutionSearch(String searchTerm){
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        findElement(institutionResult).click();

        return this;
    }

    default HeaderPage fundSearch(String searchTerm){
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        findElement(fundResult).click();

        return this;
    }


    default SecurityOverviewPage smallstockQuote(){
        findElement(securityButton).click();

        return new SecurityOverviewPage(getDriver());
    }

    //this opens the chat, but where should all the chat info go?
    default void openChat(){
        findElement(chatButton).click();
    }

    //is this in the abstract? - Need to look into that
    default void openProfile(){
        findElement(profileButton).click();
    }


    //in profile
    default void clickReleaseNotes(){
        findElement(releaseNotesButton).click();
    }

    default void leaveFeedback(){
        findElement(leaveFeedbackButton).click();
        //this could be difficult for nightly runs
    }

    default void changePassword(){
        findElement(changePasswordButton).click();
        //this could be difficult for nightly runs
    }

    default void logoutFromPage(){
        findElement(logoutButton).click();
        waitForElementToAppear(logoutConfirmation);
        findElement(logoutConfirmation).click();
    }

}
