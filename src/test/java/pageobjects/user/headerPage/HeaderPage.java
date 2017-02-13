package pageobjects.user.headerPage;

import org.openqa.selenium.*;
import pageobjects.PageObject;
import pageobjects.user.securityPage.SecurityOverviewPage;

import java.rmi.server.ExportException;

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

    //logout buttons
    By logoutOptionsField = By.xpath("//div[contains(@class,'x-msgbox') and contains(@class,'x-floating')]");
    By logoutConfirmation = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'Yes')]");
    By logoutRejection = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'No')]");

    //change password buttons/fields
    By cancelChangePass = By.xpath("//span[contains(text(),'Cancel')]");
    By postChangePassBtn = By.xpath("//span[contains(text(),'Post')]");
    By currentPassField = By.xpath("//input[contains(@name,'oldPass')]");
    By newPassField = By.xpath("//input[contains(@name,'newPass')]");
    By confirmNewPassField = By.xpath("//input[contains(@name,'confirmPass')]");
    By changePassField = By.xpath("//form[contains(@class,'settings-change-password')]");

    //change password error messages
    //These selectors are for the hidden fields,
    //So if you're looking for one of these fields, you search if they're NOT there, with these selectors.
    By noCurrentPassHidden = By.xpath("//div[contains(@class,'x-hidden-display')]//div[contains(text(),'You must enter in your current password')]");

    //feedback buttons
    By postFeedbackButton = By.xpath("//div[contains(@class,'submit-button')]");
    By cancelFeedback = By.xpath("//div[contains(@class,'cancel-button')]");
    By emptyFieldMessage = By.xpath("//div[contains(@class,'x-msgbox')]/div[contains(text(),'Message is empty')]");
    By msgboxOKBtn = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'OK')]");
    By sucessfulSubmittionField = By.xpath("//div[contains(text(),'Thank you')]");
    By feedbackMsgField = By.xpath("//textarea[contains(@name,'message')]");


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

    default boolean leaveBlankFeedback(){
        findElement(leaveFeedbackButton).click();
        findElement(postFeedbackButton).click();

        waitForElementToAppear(emptyFieldMessage);


        if(getDriver().findElement(msgboxOKBtn).isDisplayed()){
            findElement(msgboxOKBtn).click();
            return true;
        }

        return false;
    }

    default boolean leaveFeedback(String feedbackMsg){
        findElement(leaveFeedbackButton).click();
        findElement(feedbackMsgField).sendKeys(feedbackMsg);

        findElement(cancelFeedback).click();

        try {
            if (getDriver().findElement(feedbackMsgField).isDisplayed()) {
                return false;
            }
        }

        catch(Exception e){
            return true;

        }
        return false;

    }

    default boolean cancelChangePassword(){
        findElement(changePasswordButton).click();
        findElement(cancelChangePass).click();

        try{
            if(getDriver().findElement(changePassField).isDisplayed()){
                return false;
            }
        }

        catch(Exception e){
            return true;
        }

        return false;

    }

    default boolean noCurrentPassword(String newPass){
        findElement(changePasswordButton).click();
        findElement(newPassField).sendKeys(newPass);
        findElement(confirmNewPassField).sendKeys(newPass);

        findElement(postChangePassBtn).click();

        try{
            if(getDriver().findElement(noCurrentPassHidden).isDisplayed()){
                return true;
            }
        }

        catch(Exception e){
            return false;
        }

        return false;
    }

    default void logoutFromPage(){
        findElement(logoutButton).click();
        waitForElementToAppear(logoutConfirmation);
        findElement(logoutConfirmation).click();
    }

    default boolean cancelLogoutFromPage(){
        findElement(logoutButton).click();
        waitForElementToAppear(logoutRejection);
        findElement(logoutRejection).click();
        try{
            if(getDriver().findElement(logoutOptionsField).isDisplayed())
                return false;
        }

        catch(Exception e ){
            return true;
        }
        return false;
    }

}
