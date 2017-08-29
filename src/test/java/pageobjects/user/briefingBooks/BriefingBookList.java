package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickp on 2016-08-10.
 */

public class BriefingBookList extends AbstractPageObject {

    private final By reportList = By.xpath("//*[contains(@class,'briefing-book-list')]//div[contains(@class,'x-dataview-container')]");
    private final By emptyList =By.xpath("//div[contains(@class,'briefing-book-list')]//div[contains(@class,'x-dataview-emptytext')]");
    private final By createBookButton = By.cssSelector(".btn.x-button.x-unsized:not(.btn-block)");
    private final By newBriefingBook = By.cssSelector(".briefing-book-item:nth-child(1)");
    private final By checkbox = By.className("checkmark");
    private final By bulkCheckbox = By.xpath(("//div[contains(@class,'bulk-checkbox')]"));
    private final By deleteButton = By.xpath("//div[contains(@class,'bulk-button')]");
    private final By confirmDeleteButton = By.cssSelector(".q4-message-modal .x-button.primary");
    private final By searchBox = By.xpath("//div[contains(@class,'briefing-book-toolbar')]//input");
    private final By briefingBookTitle = By.cssSelector(".briefing-book-item .row div:nth-child(2)");
    private final By generalBriefingBookItem = By.xpath("//div[contains(@class,'briefing-book-item')]");
    private final By titleHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon')][.//span[contains(text(),'Title')]]");
    private final By authorHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon')][.//span[contains(text(),'Author')]]");
    private final By createdHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon')][.//span[contains(text(),'Created')]]");
    private final By updatedHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon')][.//span[contains(text(),'Last Update')]]");

    public BriefingBookList(WebDriver driver) {
        super(driver);
    }

    private ArrayList<WebElement> getBriefingBookListItems() {
        waitForLoadingScreen();
        ArrayList<WebElement> briefingBooks = new ArrayList<>(findElements(generalBriefingBookItem));
        return briefingBooks;
    }

    private boolean isSortedByAlpha(BriefingBookColumnType type){
        ArrayList<WebElement> items = getBriefingBookListItems();
        ArrayList<WebElement> strings = new ArrayList<>();
        String searchString;

        if(type==BriefingBookColumnType.AUTHOR){
                searchString=".//div[contains(@class,'medium') and contains(@class, 'column')]";
        }
        else{
            searchString=".//div[contains(@class,'flex') and contains(@class, 'column')]";
        }

        for(WebElement item : items){
            strings.add(item.findElement(By.xpath(searchString)));
            System.out.print(item.findElement(By.xpath(searchString)).getText()+"\n");
        }
        return elementsAreAlphaUpSortedIgnoreCase(strings);
    }

    private boolean isSortedByDate(BriefingBookColumnType type){
        ArrayList<WebElement> items = getBriefingBookListItems();
        ArrayList<WebElement> strings = new ArrayList<>();
        String searchString;

        if(type==BriefingBookColumnType.CREATED){
            searchString=".//div[contains(@class,'small') and contains(@class, 'column')][position()=1]";
        }
        else{
            searchString=".//div[contains(@class,'small') and contains(@class, 'column')][last()]";
        }
        for(WebElement item : items){
            strings.add(item.findElement(By.xpath(searchString)));
            System.out.print(item.findElement(By.xpath(searchString)).getText()+"\n");
        }
        return elementsAreDateUpSorted(strings);
    }

    public boolean isSortedBy(BriefingBookColumnType type){
        switch(type){
            case TITLE : case AUTHOR:
                return isSortedByAlpha(type);
            case LAST_UPDATED: case CREATED:
                return isSortedByDate(type);
        }
        return false;
    }

    public String getBriefingBookList(){
        waitForLoadingScreen();
        return findElement(reportList).getText();

    }

    public CreateBriefingBookModal addNewBriefingBook() {
        waitForLoadingScreen();
        findElement(createBookButton).click();

        return new CreateBriefingBookModal(getDriver());
    }

    public BriefingBookDetailsPage viewNewBriefingBook() {
        waitForLoadingScreen();
        waitForElementToBeClickable(newBriefingBook);
        waitForElementToRest(newBriefingBook, 300L).click();

        return new BriefingBookDetailsPage(getDriver());
    }

    public BriefingBookList deleteNewBriefingBook(){
        findElement(checkbox).click();
        findElement(deleteButton).click();
        waitForElementToAppear(confirmDeleteButton);
        findElement(confirmDeleteButton).click();
        waitForElementToRest(reportList, 1000L);
        return this;
    }

    public BriefingBookList deleteAllBriefingBooks(){
        waitForLoadingScreen();
        if(!waitForAnyElementToAppear(bulkCheckbox).getAttribute("class").contains("x-item-disabled")){
        findVisibleElement(bulkCheckbox).click();
            findVisibleElement(deleteButton).click();
            waitForElementToAppear(confirmDeleteButton);
            findVisibleElement(confirmDeleteButton).click();
        }
        waitForLoadingScreen();
        return this;
    }

    public BriefingBookList searchFor(String searchTerm){
        waitForLoadingScreen();
        findVisibleElement(searchBox).sendKeys(" ");
        findVisibleElement(searchBox).clear();
        findVisibleElement(searchBox).sendKeys(searchTerm);
        waitForElementToRest(reportList, 1000L);
        return this;
    }

    public boolean briefingBooksAreDisplayed(){
        waitForLoadingScreen();
        return !findElement(emptyList).getText().contains("No briefing books available.");
    }

    public boolean allTitlesContain(String term){
        waitForLoadingScreen();
        List<WebElement> titles = findElements(briefingBookTitle);
        if (titles.size()==0){
            System.out.println("Results are not displayed.");
            return false;
        }
        for (WebElement title : titles){
            if (!title.getText().toLowerCase().contains(term.toLowerCase())){
                System.out.println("Briefing book title: "+title.getText()+"\n\tdoes not contain term: "+term);
                return false;
            }
        }
        return true;
    }

    public void clickHeader(BriefingBookColumnType column){
        waitForLoadingScreen();
        switch(column){
            case TITLE:
                findElement(titleHeader).click();
                break;
            case AUTHOR:
                findElement(authorHeader).click();
                break;
            case CREATED:
                findElement(createdHeader).click();
                break;
            case LAST_UPDATED:
                findElement(updatedHeader).click();
                break;
        }
        if (doesElementExist(generalBriefingBookItem)) {
            waitForElementToRest(generalBriefingBookItem, 1000L);
        }
    }

    public BriefingBookList waitForListToUpdate() {
        waitForElement(reportList);
        waitForElementToRest(reportList, 1000L);
        return this;
    }
}
