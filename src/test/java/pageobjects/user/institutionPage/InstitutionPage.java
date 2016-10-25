package pageobjects.user.institutionPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.Page;

import java.util.List;

/**
 * Created by patrickp on 2016-08-08.
 */
public class InstitutionPage extends Page {

    private final By institutionName  = By.className("page-title");
    private final By institutionIcon = By.className("institution");

    public InstitutionPage(WebDriver driver) {
        super(driver);
    }


    public String getInstitutionName() {
        waitForElementToAppear(institutionIcon);
        List<WebElement> institutionNames = findElements(institutionName);
        for (int i=0; i<institutionNames.size(); i++){
            if (institutionNames.get(i).isDisplayed()){
                return institutionNames.get(i).getText();
            }
        }
        return findElement(institutionName).getText();
    }
}
