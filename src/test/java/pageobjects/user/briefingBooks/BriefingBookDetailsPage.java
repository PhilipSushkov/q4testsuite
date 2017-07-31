package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;

/**
 * Created by patrickp on 2016-09-13.
 */
public class BriefingBookDetailsPage extends AbstractPageObject {
    private final By deleteButton = By.xpath("//span[contains(text(),'Name')]//following::span[contains(@class,'q4i-trashbin-4pt')]");
    private final By heroDeleteButton = By.xpath("//div[contains(@class,'action-button')][.//span[contains(@class,'q4i-trashbin-4pt')]]");
    private final By deleteConfirmation = By.xpath("//*[contains(text(), 'Yes')]");
    private final By saveButton = By.xpath("//div[contains(@class,'x-button-no-icon') and ./span[contains(text(),'Save')]]");
    private final By addButton = By.xpath("//div[contains(@class, 'x-button')][span[contains(@class, 'q4i-add-4pt')]]");
    private final By entityTypeToggle = By.className("x-toggle");
    private final By institutionOption = By.className("institution");
    private final By fundOption = By.className("fund");
    private final By contactOption = By.className("contact");
    private final By entitySearchBox = By.cssSelector(".note-link-field .x-input-search");
    private final By entityResults = By.className("result-item");
    private final By firstEntityResult = By.xpath("//div[contains(@class,'result-item')][1]");
    private final By saveEntityButton = By.cssSelector(".form-button.yellow");
    private final By entityList = By.className("briefing-book-detail-list");
    private final By editButton = By.xpath("//div[contains(@class,'x-button-no-icon') and ./span[contains(text(),'Edit')]]");
    private final By cancelButton = By.xpath("//div[contains(@class,'x-button-no-icon') and ./span[contains(text(),'Cancel')]]");
    private final By generalEntity = By.xpath("//div[contains(@class,'x-list-item')]");
    private final By deleteConfirmationPopUp = By.className("x-floating");
    private final By entityDragHandle = By.className("x-list-sortablehandle");
    private final By topOfEntityList = By.className("bulk-action-toolbar");
    private final By entityName = By.cssSelector(".x-list-item .name");


    Actions actions = new Actions(driver);

    public BriefingBookDetailsPage(WebDriver driver) {
        super(driver);
    }

    public BriefingBookList deleteBriefingBookFromDetailsPage() {
        pause(2000);
        findVisibleElement(heroDeleteButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteConfirmation));
        findElement(deleteConfirmation).click();
        return new BriefingBookList(getDriver());
    }

    public BriefingBookDetailsPage addInstitution(String name) {
        waitForLoadingScreen();
        waitForAnyElementToAppear(addButton);
        findVisibleElement(addButton).click();
        waitForElement(entityTypeToggle);
        findVisibleElement(entityTypeToggle).click();
        findElement(institutionOption).click();
        findElement(entitySearchBox).sendKeys(name);
        waitForElement(entityResults);
        findElement(firstEntityResult).click();
        findElement(saveEntityButton).click();
        return this;
    }

    public BriefingBookDetailsPage addFund(String name) {
        waitForLoadingScreen();
        waitForAnyElementToAppear(addButton);
        findVisibleElement(addButton).click();
        waitForElement(entityTypeToggle);
        findVisibleElement(entityTypeToggle).click();
        findElement(fundOption).click();
        findElement(entitySearchBox).sendKeys(name);
        waitForElement(entityResults);
        findElement(firstEntityResult).click();
        findElement(saveEntityButton).click();
        return this;
    }

    public BriefingBookDetailsPage addContact(String name) {
        waitForLoadingScreen();
        waitForAnyElementToAppear(addButton);
        findVisibleElement(addButton).click();
        waitForElement(entityTypeToggle);
        findVisibleElement(entityTypeToggle).click();
        findElement(contactOption).click();
        findElement(entitySearchBox).sendKeys(name);
        waitForElement(entityResults);
        findElement(firstEntityResult).click();
        findElement(saveEntityButton).click();
        return this;
    }

    public BriefingBookDetailsPage clickEdit() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        findElement(editButton);
        return this;
    }

    public BriefingBookDetailsPage clickCancel() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        findElement(editButton);
        return this;
    }

    public BriefingBookDetailsPage clickSave() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        findElement(editButton);
        return this;
    }


    public BriefingBookDetailsPage deleteEntity(String name){
            startDeleteForEntry(name);
            confirmDelete();
            return this;
    }


    public WebElement returnEntity(String name){
        ArrayList<WebElement> entities = new ArrayList<>();
        try {
           entities = returnTableEntities();
        }
        catch (Exception e) {
            System.out.print("Entity not present");
        }
            for (WebElement entity : entities) {
                if(entity.isDisplayed()) {
                    if (entity.findElement(By.className("name")).getText().equals(name)) {
                        return entity;
                    }
                }
            }
            return null;
    }

    public boolean doesEntityExist(String name){
        if(returnEntity(name)==null){
            return false;
        }
        else{
            return true;
        }
    }

    public BriefingBookDetailsPage startDeleteForEntry(String name){
            WebElement entityToDelete = null;
            entityToDelete=returnEntity(name);
            if(entityToDelete!=null){
                entityToDelete.findElement(By.xpath(".//div[contains(@class,'checkmark') and contains(@class,'checkbox')]")).click();
                pause(1000);
                findElement(deleteButton).click();
            }
            return this;
    }

    private BriefingBookDetailsPage cancelDelete(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteConfirmationPopUp));
        findElement(deleteConfirmationPopUp).findElement(By.xpath("//div[contains(@class,'x-button-no-icon') and ./span [contains(text(),'No')]]")).click();
        waitForLoadingScreen();
        return this;
    }

    private BriefingBookDetailsPage confirmDelete(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteConfirmationPopUp));
        findElement(deleteConfirmationPopUp).findElement(By.xpath("//div[contains(@class,'x-button-no-icon') and ./span [contains(text(),'Yes')]]")).click();
        waitForLoadingScreen();
        return this;
    }

    private ArrayList<WebElement> returnTableEntities() {
        waitForLoadingScreen();
        ArrayList<WebElement> entities = new ArrayList<>(findElements(generalEntity));
        return entities;
        }

    public String getEntityList(){
        waitForLoadingScreen();
        return findElement(entityList).getText();
    }

    public String getEntity(int index){
        waitForLoadingScreen();
        waitForElement(entityName);
        return findElements(entityName).get(index).getText();
    }

    public void reorderEntityToBeginning(int originIndex){
        waitForLoadingScreen();
        actions.dragAndDrop(findElements(entityDragHandle).get(originIndex), findElement(topOfEntityList)).perform();
        waitForElementToRest(topOfEntityList, 1000L);
        driver.navigate().refresh();
    }

}
