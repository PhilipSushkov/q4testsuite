package pageobjects.user.estimatesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-24.
 */
public class SecurityEstimatesPage extends AbstractPageObject{

    private final By tabTitle = By.cssSelector(".company-header .menu-button .x-button-label");

    public SecurityEstimatesPage(WebDriver driver) {
        super(driver);
    }

    public String getEstimatesTabTitle() {

        waitForLoadingScreen();
        return findElement(tabTitle).getText();
    }
}
