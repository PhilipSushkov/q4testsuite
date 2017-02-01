package pageobjects.user.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickp on 2016-08-25.
 */
public class ContactPage extends AbstractPageObject {

    private final By contactList = By.cssSelector(".contact-favorite-list");
    private final By firstContactInList = By.cssSelector(".contact-favorite-list-container .contact-favorite-list .x-dataview-item .column:nth-child(2)");
    private final By searchField = By.name("contactsFilterSearch");
    private final By showMoreLink = By.cssSelector(".load-more .x-button");

    private final By nameSort = By.xpath("//div[contains(@class,'x-button') and contains(@class,'name')]");
    private final By locationSort = By.xpath("//div[contains(@class,'x-button') and contains(@class,'location')]");
    private final By phoneSort = By.xpath("//div[contains(@class,'x-button') and contains(@class,'phone')]");

    private final By contactDelete = By.xpath("//div[contains(@class,'checkbox-mask')]");
    private final By deleteButton = By.cssSelector(".x-button-icon.q4i-trashbin-4pt");
    private final By confirmDeletion = By.xpath("//span[contains(text(),'Yes')]");
    private final By refusedDeletion = By.xpath("//span[contains(text(),'No')]");

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    private List<WebElement> returnTableRows (){
        List<WebElement> rowList = findElements(By.xpath("//div[contains(@class,'x-dataview-container')]//div[contains(@class,'row')]"));
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;

    }

    public String getContacts() {
        waitForLoadingScreen();
        loadAllContacts();
        return findElement(contactList).getText();
    }

    public void loadAllContacts(){
            while(findElement(showMoreLink).isDisplayed()) {
                findElement(showMoreLink).click();
                waitForLoadingScreen();
            }
    }

    public ContactDetailsPage viewContactDetails() {
        findElement(firstContactInList).click();
        
        return new ContactDetailsPage(getDriver());
    }


    public void deleteFromList(){
        findElement(contactDelete).click();
        findElement(deleteButton).click();
        waitForLoadingScreen();
        findElement(confirmDeletion).click();

    }

    public ContactPage searchForContact(String name) {
        findElement(searchField).click();
        findElement(searchField).sendKeys(name);
        findElement(searchField).sendKeys(Keys.ENTER);
        waitForLoadingScreen();

        return this;
    }

    public boolean isColumnAscending(ContactColumnType column){
        By selector = getColumnSelector(column);
        return findElement(selector).getAttribute("class").contains("asc");

    }

    public ContactPage clickColumnHeader(ContactColumnType column){
        By selector = getColumnSelector(column);
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(selector));
        findElement(selector).click();
        return this;
    }


    //Checking if the columns are sorted


    public boolean isNameSorted(List<WebElement> rows){
        ArrayList<WebElement> names = new ArrayList<>();
        for(WebElement i : rows){
            names.add(i.findElement(By.className("name")));
        }
        if(isColumnAscending(ContactColumnType.NAME)){
            return elementsAreAlphaUpSorted(names);
        }
        else
            return elementsAreAlphaDownSorted(names);
    }


    public boolean isLocationSorted(List<WebElement> rows){
        ArrayList<WebElement> locations = new ArrayList<>();
        for(WebElement i : rows){
            locations.add(i.findElement(By.className("location")));
        }
        if(isColumnAscending(ContactColumnType.LOCATION)){
            return elementsAreAlphaUpSorted(locations);
        }
        else
            return elementsAreAlphaDownSorted(locations);
    }


    public boolean isPhoneSorted(List<WebElement> rows){
        ArrayList<WebElement> phoneNums = new ArrayList<>();
        for(WebElement i : rows){
            phoneNums.add(i.findElement(By.className("phone")));
        }
        if(isColumnAscending(ContactColumnType.PHONE)){
            return elementsAreNumUpSorted(phoneNums);
        }
        else
            return elementsAreNumDownSorted(phoneNums);
    }


    //Redirecting methods to the right column

    public boolean isColumnSorted(ContactColumnType column){
        waitForLoadingScreen();
        switch(column){
            case NAME:
                return isNameSorted(returnTableRows());
            case LOCATION:
                return isLocationSorted(returnTableRows());
            case PHONE:
                return isPhoneSorted(returnTableRows());
            case EMAIL:
                break;
        }
        return false;
    }

    private By getColumnSelector(ContactColumnType column){
        By selector =null;

        switch(column){
            case NAME:
                selector = nameSort;
                break;
            case LOCATION:
                selector = locationSort;
                break;
            case PHONE:
                selector = phoneSort;
                break;
            case EMAIL:
                break;
        }
        return selector;
    }
}
