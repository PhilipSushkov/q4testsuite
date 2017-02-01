package pageobjects.user.headerPage;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import pageobjects.user.securityPage.SecurityOverviewPage;

/**
 * Created by sarahr on 2/1/2017.
 */
public class HeaderPage extends AbstractPageObject{

    //selectors

    //Header buttons
    private final By searchBar = By.cssSelector("#ext-element-208");
    private final By securityButton = By.xpath("//div[contains(@class,'company-details')]");
    private final By chatButton = By.xpath("//div[contains(@class,'chat-button')]");
    private final By profileButton = By.xpath("//div[contains(@class,'profile-inner-content')]");

    //When Profile is open, these buttons appear
    private final By leaveFeedbackButton = By.xpath("");
    private final By changePasswordButton = By.xpath("");
    private final By logoutButton = By.xpath("");

    //

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    public HeaderPage headerSearch(String searchTerm){
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        return this;
    }

    public SecurityOverviewPage securityButton(){
        findElement(securityButton).click();

        return new SecurityOverviewPage(getDriver());
    }

    //this opens the chat, but where should all the chat info go?
    public void openChat(){
        findElement(chatButton).click();
    }


    //is this in the abstract? - Need to look into that
    public void openProfile(){
        findElement(profileButton).click();
    }

    //in profile

    public void leaveFeedback(){
        findElement(leaveFeedbackButton).click();
    }

    public void changePassword(){
        findElement(changePasswordButton).click();
    }

    //logout is already in the abstract? Along with something called reportHeader? Not sure.



}
