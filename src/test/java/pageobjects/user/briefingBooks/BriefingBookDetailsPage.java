package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-13.
 */
public class BriefingBookDetailsPage extends AbstractPageObject{
    private final By deleteButton = By.cssSelector(".q4-hero-banner .x-iconalign-left");
    private final By deleteConfirmation = By.xpath("//*[contains(text(), 'Yes')]");
    private final By saveButton = By.cssSelector(".bulk-toolbar .x-button-no-icon:nth-child(3)");
    private final By addButton = By.cssSelector(".bulk-toolbar .x-button-no-icon.add");
    private final By editButton = By.cssSelector(".bulk-toolbar .x-button-no-icon:not(.add)");
    private final By entityTypeToggle = By.className("x-toggle");
    private final By institutionOption = By.className("q4i-institution-2pt");
    private final By fundOption = By.className("q4i-fund-2pt");
    private final By contactOption = By.className("q4i-contact-2pt");
    private final By entitySearchBox = By.cssSelector(".note-link-field .x-input-search");
    private final By entityResults = By.className("result-item");
    private final By saveEntityButton = By.cssSelector(".form-button.yellow");
    private final By entityList = By.className("briefing-book-detail-list");
    private final By topOfEntityList = By.className("bulk-toolbar");
    private final By entityName = By.cssSelector(".x-list-item .name");
    private final By entityDragHandle = By.className("x-list-sortablehandle");

    Actions actions = new Actions(driver);

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

    public BriefingBookDetailsPage addInstitution(String name){
        waitForLoadingScreen();
        findElement(addButton).click();
        pause(500);
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
        pause(500);
        findVisibleElement(entityTypeToggle).click();
        findElement(fundOption).click();
        findElement(entitySearchBox).sendKeys(name);
        waitForElement(entityResults);
        findElement(entityResults).click();
        findElement(saveEntityButton).click();
        pause(1000);
        return this;
    }

    public BriefingBookDetailsPage addContact(String name){
        waitForLoadingScreen();
        findElement(addButton).click();
        pause(500);
        findVisibleElement(entityTypeToggle).click();
        findElement(contactOption).click();
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

    public String getEntity(int index){
        waitForLoadingScreen();
        return findElements(entityName).get(index).getText();
    }

    public void reorderEntityToBeginning(int originIndex){
        waitForLoadingScreen();
        findVisibleElement(editButton).click();
        actions.dragAndDrop(findElements(entityDragHandle).get(originIndex), findElement(topOfEntityList)).perform();
        findElement(saveButton).click();
        driver.navigate().refresh();
    }
}
