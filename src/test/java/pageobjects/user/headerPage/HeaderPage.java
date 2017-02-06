package pageobjects.user.headerPage;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
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
    By leaveFeedbackButton = By.xpath("//span[contains(@class,'item') and contains(text(),'Feedback')]");
    By changePasswordButton = By.xpath("//span[contains(@class,'item') and contains(text(),'Password')]");
    By logoutButton = By.xpath("//span[contains(@class,'item') and contains(text(),'Logout')]");

    By logoutConfirmation = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'Yes')]");
    By logoutRejection = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'No')]");

    //

    default HeaderPage headerSearch(String searchTerm){
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        return this;
    }

    default SecurityOverviewPage securityButton(){
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

    default void leaveFeedback(){
        findElement(leaveFeedbackButton).click();
    }

    default void changePassword(){
        findElement(changePasswordButton).click();
    }

    default void logoutFromPage(){
        findElement(logoutButton).click();
        findElement(logoutConfirmation).click();
    }



}
