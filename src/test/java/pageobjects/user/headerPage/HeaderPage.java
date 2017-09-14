package pageobjects.user.headerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.PageObject;
import pageobjects.user.securityPage.SecurityOverviewPage;

import java.util.ArrayList;
import java.util.List;

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
    By securityResults = By.xpath("//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[text()='security '] and following-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]]");
    By institutionResults = By.xpath("//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[text()='institution '] and following-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]]");
    By contactResults = By.xpath("//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[text()='contact '] and following-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]]");
    By fundResults = By.xpath("//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[text()='fund ']]");

    By releaseNotesPageHeader = By.xpath("//h1[contains(text(),'Release Notes')]");


    default HeaderPage contactSearch(String searchTerm, String name){
        waitForElementToBeClickable(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        waitForAnyElementToAppear(contactResults);

        List<WebElement> contacts = findElements(contactResults);
        for (WebElement contact : contacts) {
            if (contact.getText().contains(name)) {
                contact.click();
                return this;
            }
        }

        // If not found, click first element
        System.out.println("Failed to find " + name + " in search results, choosing first from list");
        findElement(contactResults).click();

        return this;
    }

    default HeaderPage securitySearch(String searchTerm, String title){
        waitForElementToBeClickable(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        waitForAnyElementToAppear(securityResults);

        List<WebElement> contacts = findElements(securityResults);
        for (WebElement contact : contacts) {
            if (contact.getText().contains(title)) {
                contact.click();
                return this;
            }
        }

        // If not found, click first element
        System.out.println("Failed to find " + title + " in search results, choosing first from list");
        findElement(securityResults).click();

        return this;
    }

    default HeaderPage institutionSearch(String searchTerm, String title){
        waitForElementToBeClickable(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        waitForAnyElementToAppear(institutionResults);

        List<WebElement> contacts = findElements(institutionResults);
        for (WebElement contact : contacts) {
            if (contact.getText().contains(title)) {
                contact.click();
                return this;
            }
        }

        // If not found, click first element
        System.out.println("Failed to find " + title + " in search results, choosing first from list");
        findElement(institutionResults).click();

        return this;
    }

    default HeaderPage fundSearch(String searchTerm, String title){
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        waitForAnyElementToAppear(fundResults);

        List<WebElement> contacts = findElements(fundResults);
        for (WebElement contact : contacts) {
            if (contact.getText().contains(title)) {
                contact.click();
                return this;
            }
        }

        // If not found, click first element
        System.out.println("Failed to find " + title + " in search results, choosing first from list");
        findElement(fundResults).click();

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
        waitForElementToBeClickable(chatButton).click();
    }

    default boolean clearChat(String message){

        waitForElementToBeClickable(chatMessageField).sendKeys(message);
        waitForElementToBeClickable(clearChatBtn).click();

        try{
            waitForElement(emptyMessageField);
            if(findElement(emptyMessageField).isDisplayed()){
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
        waitForElementToBeClickable(profileButton).click();
    }


    //in profile
    default void clickReleaseNotes(){
        waitForElementToBeClickable(releaseNotesButton).click();
        try{
            waitForElement(releaseNotesPageHeader);
        }
        catch(Exception e){
        }
    }

    default boolean leaveBlankFeedback(){
        waitForElementToBeClickable(leaveFeedbackButton).click();
        waitForElementToBeClickable(postFeedbackButton).click();

        waitForElementToAppear(emptyFieldMessage);


        if(getDriver().findElement(msgboxOKBtn).isDisplayed()){
            //findElement(msgboxOKBtn).click();
            return true;
        }

        return false;
    }

    default boolean leaveFeedback(String feedbackMsg){
        waitForElementToBeClickable(leaveFeedbackButton).click();
        waitForElementToBeClickable(feedbackMsgField).sendKeys(feedbackMsg);

        waitForElementToBeClickable(cancelFeedback).click();

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
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(cancelChangePass).click();

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
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(newPassField).sendKeys(newPass);
        waitForElementToBeClickable(confirmNewPassField).sendKeys(newPass);

        waitForElementToBeClickable(postChangePassBtn).click();


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
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(currentPass);

        waitForElementToBeClickable(postChangePassBtn).click();

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
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(currentPass);
        waitForElementToBeClickable(newPassField).sendKeys(newPass);

        waitForElementToBeClickable(postChangePassBtn).click();

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
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(currentPass);
        waitForElementToBeClickable(newPassField).sendKeys(newPass);

        waitForElementToBeClickable(postChangePassBtn).click();

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
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(currentPass);
        waitForElementToBeClickable(newPassField).sendKeys(newPass);
        waitForElementToBeClickable(confirmNewPassField).sendKeys(newPass);

        waitForElementToBeClickable(postChangePassBtn).click();

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
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(password);
        waitForElementToBeClickable(newPassField).sendKeys(password);
        waitForElementToBeClickable(confirmNewPassField).sendKeys(password);

        waitForElementToBeClickable(postChangePassBtn).click();

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
        waitForElement(logoutButton);
        WebElement staleElement = findElement(logoutButton);
        findElement(logoutButton).click();
        getWait().until(ExpectedConditions.stalenessOf(staleElement));
    }

    default By getSearchResultsBetween(String first, String last) {
        String path;
        if (last != null) {
            path = "//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[text()='"
            + first + "'] and following-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[@class='type'][text()='"
            + last + "']]";
        } else {
            path = "//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[text()='"
                    + first + "']]";
        }
        return By.xpath(path);
    }
}
