package pageobjects.user.targeting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-19.
 */
public class TargetingPage extends AbstractPageObject {

    private final By newSearchButton = By.cssSelector(".q4-hero-banner .x-dock .action-button");
    //private final By showSearches = By.id("ext-tab-1");
    //private final By showTargets = By.id("ext-tab-2");
    //private final By showInstitutions = By.cssSelector(".range-tabs-inner:nth-child(1)");
    //private final By showFunds = By.id("ext-button-271");
    //private final By showContacts = By.id("ext-button-272");

    public TargetingPage(WebDriver driver) {
        super(driver);
    }

    //navigation to other pages here
    public NewSearchPage newSearch(){
        waitForElementToAppear(newSearchButton);
        findElement(newSearchButton).click();

        return new NewSearchPage(getDriver());

    }

    //other methods here
}
