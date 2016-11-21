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
    private final By researchHeadlineColumnHeader = By.cssSelector(".research-list .list-header .x-button.headline, .research-list .list-item .column.headline");
    private final By researchHeadline = By.cssSelector(".list-item .column.headline");
    private final By researchDateColumnHeader = By.cssSelector(".research-list .list-header .x-button:first-child, .research-list .list-item .column:first-child");
    private final By researchDate = By.cssSelector(".research-list .list-item .column:first-child");
    private final By researchFirmColumnHeader = By.cssSelector("");
    private final By researchFirm = By.cssSelector("");


    //x-container x-unsized x-size-monitored x-paint-monitored list-header

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
        pause(1000L);

        return new ContactPage(getDriver());
    }

    public boolean sortByHeadline() {
        waitForLoadingScreen();
        // sorting by headline ascending
        findVisibleElement(researchHeadlineColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(researchHeadline))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        // sorting by headline ascending
        findVisibleElement(researchHeadlineColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(researchHeadline))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        return true;
    }

    public boolean sortByDate() {
        waitForLoadingScreen();
        // sorting by headline ascending
        findVisibleElement(researchDateColumnHeader).click();
        pause(300);
        if (!elementsAreDateUpSorted(findVisibleElements(researchDate))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        // sorting by headline ascending
        findVisibleElement(researchDateColumnHeader).click();
        pause(300);
        if (!elementsAreDateDownSorted(findVisibleElements(researchDate))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        return true;
    }

    public boolean sortByFirm() {
        waitForLoadingScreen();
        // sorting by headline ascending
        findElement(researchFirmColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(researchFirm))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        // sorting by headline ascending
        findVisibleElement(researchFirmColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(researchFirm))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        return true;
    }
}

