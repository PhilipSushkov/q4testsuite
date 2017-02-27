package pageobjects.user.volatilityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by kelvint on 11/1/16.
 */
public class VolatilityPage extends AbstractPageObject {

    private final By nlg = By.id("ext-templates-natural-indicators-language-3");

    public VolatilityPage(WebDriver driver) {
        super(driver);
    }


    public String getNlgText() {
        waitForLoadingScreen();
        return findElement(nlg).getText();
    }

    public boolean verifyTextIsPresent() {
        String nglText = getNlgText();
        if (nglText.isEmpty()) {
            {
                return false;
            }
        } else {
            return true;
        }
    }
}
