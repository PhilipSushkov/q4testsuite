package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-13.
 */
public class BriefingBookDetailsPage extends AbstractPageObject{
    private final By deleteButton = By.cssSelector(".q4-hero-banner .x-iconalign-left");
    private final By deleteConfirmation = By.xpath("//*[contains(text(), 'Yes')]");
    private final By saveButton = By.cssSelector(".q4-hero-banner .action-button + .action-button");
    private final By addButton = By.cssSelector(".bulk-toolbar .x-button-no-icon");
    private final By entityTypeToggle = By.className("x-toggle");
    private final By institutionOption = By.className("q4i-institution-2pt");
    private final By fundOption = By.className("q4i-fund-2pt");
    private final By entitySearchBox = By.cssSelector(".note-link-field .x-input-search");
    private final By entityResults = By.className("result-item");
    private final By saveEntityButton = By.cssSelector(".form-button.yellow");
    private final By entityList = By.className("briefing-book-detail-list");

    public BriefingBookDetailsPage(WebDriver driver) {
        super(driver);
    }

    public BriefingBookList deleteBriefingBookFromDetailsPage() {
        pause(2000);
        findVisibleElement(deleteButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteConfirmation));
        findElement(deleteConfirmation).click();

        return new BriefingBookList(getDriver());
    }

    public BriefingBookDetailsPage saveChanges() {
        pause(500L);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        findElement(saveButton).click();

        return this;
    }

    public BriefingBookDetailsPage addInstitution(String name){
        waitForLoadingScreen();
        findElement(addButton).click();
        findVisibleElement(entityTypeToggle).click();
        findElement(institutionOption).click();
        findElement(entitySearchBox).sendKeys(name);
        waitForElement(entityResults);
        findElement(entityResults).click();
        findElement(saveEntityButton).click();
        pause(1000);
        return this;
    }

    public BriefingBookDetailsPage addFund(String name){
        waitForLoadingScreen();
        findElement(addButton).click();
        findVisibleElement(entityTypeToggle).click();
        findElement(fundOption).click();
        findElement(entitySearchBox).sendKeys(name);
        waitForElement(entityResults);
        findElement(entityResults).click();
        findElement(saveEntityButton).click();
        pause(1000);
        return this;
    }

    public String getEntityList(){
        return findElement(entityList).getText();
    }
}
