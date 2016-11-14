package pageobjects.user.researchPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.institutionPage.InstitutionPage;

/**
 * Created by patrickp on 2016-11-09.
 */
public class ResearchPage extends AbstractPageObject {
    private final By researchSearchField = By.cssSelector(".toolbar-panel .search .x-field-input .x-input-el");
    private final By researchSearchResults = By.cssSelector(".active");
    private final By numberOfDocuments = By.cssSelector(".count");
    private final By firmName = By.cssSelector(".institution");
    private final By analystName = By.cssSelector("div.contact:nth-child(1) > a:nth-child(1) > span:nth-child(2)");
    private final By multipleAnalystDropdown = By.cssSelector(".multiple");

    public ResearchPage(WebDriver driver) {
        super(driver);
    }

    public ResearchPage searchForDocument(String documentName) {
        waitForLoadingScreen();
        findElement(researchSearchField).sendKeys(documentName);
        findElement(researchSearchField).sendKeys(Keys.ENTER);
        waitForLoadingScreen();

        return this;
    }

    public String getResearchHeadline() {
        return findElement(researchSearchResults).getText();
    }

    public int getNumberOfDocuments() {
        return Integer.parseInt(findElement(numberOfDocuments).getText().replaceAll("[()]", ""));
    }

    public String getFirmNameFromList() {
        return findElement(firmName).getText();
    }

    public InstitutionPage selectFirmFromResearchList() {
        waitForLoadingScreen();
        findElement(firmName).click();

        return new InstitutionPage(getDriver());
    }

    public String getAnalystNameFromList() {
        return findElement(analystName).getText();
    }

    public ResearchPage viewMultipleAnaysts() {
        findElement(multipleAnalystDropdown).click();

        return this;
    }

    public ContactPage selectAnalystFromResearchList() {
        findElement(analystName).click();

        return new ContactPage(getDriver());
    }
}
