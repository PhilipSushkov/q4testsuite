package pageobjects.user.fundPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;

/**
 * Created by patrickp on 2016-08-08.
 */
public class FundPage extends Page {
    private final By fundName = By.className("company-details");

    public FundPage(WebDriver driver) {
        super(driver);
    }

    public String getFundName() {
        waitForLoadingScreen();
        waitForAnyElementToAppear(fundName);
        return findVisibleElement(fundName).getText();
    }
}
