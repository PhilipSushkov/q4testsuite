package pageobjects.user.targeting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.io.IOException;
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
}
