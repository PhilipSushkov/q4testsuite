package pageobjects.user.fundPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.Page;

import java.util.List;

/**
 * Created by patrickp on 2016-08-08.
 */
public class FundPage extends Page {
    private final By fundName = By.className("page-title");
    private final By fundIcon = By.className("fund");

    public FundPage(WebDriver driver) {
        super(driver);
    }

    public String getFundName() {
        waitForElementToAppear(fundIcon);
        List<WebElement> fundNames = findElements(fundName);
        for (int i=0; i<fundNames.size(); i++){
            if (fundNames.get(i).isDisplayed()){
                return fundNames.get(i).getText();
            }
        }
        return findElement(fundName).getText();
    }
}
