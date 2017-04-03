package pageobjects.user.headerPage;

import org.openqa.selenium.By;
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

    //chat buttons
    By chatMessageField = By.xpath("//textarea[contains(@name,'message')]");
    By clearChatBtn = By.xpath("//form//div[contains(@class,'clear')]//span[contains(text(),'Clear')]");
    By emptyMessageField = By.xpath("//form[contains(@class,'chat-form')]//div[contains(@class,'x-empty')]");

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
    By noCurrentPassMsg = By.xpath("//div[contains(@class,'x-msgbox-text')]//div[contains(text(),'You must enter in your current password')]");
    By noNewPassMsg = By.xpath("//div[contains(@class,'x-msgbox-text')]//div[contains(text(),'You must enter in your new password')]");
    By notSecurePassMsg = By.xpath("//div[contains(@class,'x-msgbox-text')]//div[contains(text(),'Password must contain')]");
    By noConfirmationPassMsg = By.xpath("//div[contains(@class,'x-msgbox-text')]//div[contains(text(),'password do not match')]");
    By wrongOldPassMsg = By.xpath("//div[contains(@class,'x-msgbox-text')]//div[contains(text(),'Old password is incorrect')]");
    By samePassAsBeforeMsg = By.xpath("//div[contains(@class,'x-msgbox-text')]//div[contains(text(),'should not match the current password')]");


    //feedback buttons
    By postFeedbackButton = By.xpath("//span[contains(@class,'x-button-label') and contains(text(),'Post')]");
    By cancelFeedback = By.xpath("//span[contains(@class,'x-button-label') and contains(text(),'Cancel')]");
    By emptyFieldMessage = By.xpath("//div[contains(@class,'x-msgbox')]/div[contains(text(),'Message is empty')]");
    By msgboxOKBtn = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'OK')]");
    By sucessfulSubmittionField = By.xpath("//div[contains(text(),'Thank you')]");
    By feedbackMsgField = By.xpath("//textarea[contains(@name,'message')]");


    //Search results
    By securityResult = By.xpath("//div[contains(@class,'list-header') and contains(@class,'security')]/../../following-sibling::div[1]");
    By institutionResult = By.xpath("//div[contains(@class,'list-header') and contains(@class,'institution')]/../../following-sibling::div[1]");
    By contactResult = By.xpath("//div[contains(@class,'list-header') and contains(@class,'contact')]/../../following-sibling::div[1]");
    By fundResult = By.xpath("//div[contains(@class,'list-header') and contains(@class,'fund')]/../../following-sibling::div[1]");

    By releaseNotesPageHeader = By.xpath("//h1[contains(text(),'Release Notes')]");



    /*
    This search method needs some work
    We need to decide how we want to search things

    Right now, you can search and click ~first~ fund, contact, etc. in the list
     */


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

    /////
    /////


    default SecurityOverviewPage smallstockQuote(){
        findElement(securityButton).click();

        return new SecurityOverviewPage(getDriver());
    }

    //this opens the chat, but where should all the chat info go?
    default void openChat(){
        findElement(chatButton).click();
    }

    default boolean clearChat(String message){

        findElement(chatMessageField).sendKeys(message);
        findElement(clearChatBtn).click();

        try{
            if(getDriver().findElement(emptyMessageField).isDisplayed()){
                return true;
            }
            else{
                return false;
            }

        }
        catch(Exception e){
            return false;
        }

    }

    default void openProfile(){
        findElement(profileButton).click();
    }


    //in profile
    default void clickReleaseNotes(){
        findElement(releaseNotesButton).click();
        try{
            waitForElement(releaseNotesPageHeader);
        }
        catch(Exception e){

        }
    }

    default boolean leaveBlankFeedback(){
        findElement(leaveFeedbackButton).click();
        findElement(postFeedbackButton).click();

        waitForElementToAppear(emptyFieldMessage);


        if(getDriver().findElement(msgboxOKBtn).isDisplayed()){
            //findElement(msgboxOKBtn).click();
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
            else{
                return true;
            }
        }

        catch(Exception e){
            return true;

        }
    }

    default boolean cancelChangePassword(){
        findElement(changePasswordButton).click();
        findElement(cancelChangePass).click();

        try{
            if(getDriver().findElement(changePassField).isDisplayed()){
                return false;
            }
            else{
                return true;
            }
        }

        catch(Exception e){
            return true;
        }


    }

    default boolean noCurrentPassword(String newPass){
        findElement(changePasswordButton).click();
        findElement(newPassField).sendKeys(newPass);
        findElement(confirmNewPassField).sendKeys(newPass);

        findElement(postChangePassBtn).click();


        try{
            waitForElementToAppear(noCurrentPassMsg);
            if(getDriver().findElement(noCurrentPassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }
    }

    default boolean noNewPassword(String currentPass){
        findElement(changePasswordButton).click();
        findElement(currentPassField).sendKeys(currentPass);

        findElement(postChangePassBtn).click();

        try{
            waitForElementToAppear(noNewPassMsg);
            if(getDriver().findElement(noNewPassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }

    default boolean notSecurePass(String currentPass, String newPass){
        findElement(changePasswordButton).click();
        findElement(currentPassField).sendKeys(currentPass);
        findElement(newPassField).sendKeys(newPass);

        findElement(postChangePassBtn).click();

        try{
            waitForElementToAppear(notSecurePassMsg);
            if(getDriver().findElement(notSecurePassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }

    default boolean noConfirmationPass(String currentPass, String newPass){
        findElement(changePasswordButton).click();
        findElement(currentPassField).sendKeys(currentPass);
        findElement(newPassField).sendKeys(newPass);

        findElement(postChangePassBtn).click();

        try{
            waitForElementToAppear(noConfirmationPassMsg);
            if(getDriver().findElement(noConfirmationPassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }

    default boolean wrongOldPass(String currentPass, String newPass){
        findElement(changePasswordButton).click();
        findElement(currentPassField).sendKeys(currentPass);
        findElement(newPassField).sendKeys(newPass);
        findElement(confirmNewPassField).sendKeys(newPass);

        findElement(postChangePassBtn).click();

        try{
            waitForElementToAppear(wrongOldPassMsg);
            if(getDriver().findElement(wrongOldPassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }

    default boolean samePassAsBefore(String password){
        findElement(changePasswordButton).click();
        findElement(currentPassField).sendKeys(password);
        findElement(newPassField).sendKeys(password);
        findElement(confirmNewPassField).sendKeys(password);

        findElement(postChangePassBtn).click();

        try{
            waitForElementToAppear(samePassAsBeforeMsg);
            if(getDriver().findElement(samePassAsBeforeMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }



    default void logoutFromPage(){
        findElement(logoutButton).click();
        waitForElementToAppear(logoutConfirmation);
        findElement(logoutConfirmation).click();
    }

}
