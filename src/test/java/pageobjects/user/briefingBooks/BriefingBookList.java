package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.util.List;

/**
 * Created by patrickp on 2016-08-10.
 */

public class BriefingBookList extends AbstractPageObject {

    private final By reportList = By.cssSelector(".briefing-book-list");
    private final By createBookButton = By.cssSelector(".q4-hero-banner .action-button");
    private final By newBriefingBook = By.cssSelector(".briefing-book-item:nth-child(1)");
    private final By checkbox = By.className("checkmark");
    private final By deleteButton = By.className("toolbar-button");
    private final By confirmDeleteButton = By.className("x-button-action");
    private final By searchBox = By.cssSelector(".briefing-book-toolbar [type=search]");
    private final By briefingBookTitle = By.cssSelector(".briefing-book-item .row div:nth-child(2)");

    public BriefingBookList(WebDriver driver) {
        super(driver);
    }

    public String getBriefingBookList() {
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
        findElement(newBriefingBook).click();

        return new BriefingBookDetailsPage(getDriver());
    }

    public BriefingBookList deleteNewBriefingBook(){
        waitForLoadingScreen();
        findElement(checkbox).click();
        findElement(deleteButton).click();
        waitForElementToAppear(confirmDeleteButton);
        findElement(confirmDeleteButton).click();
        return this;
    }

    public BriefingBookList searchFor(String searchTerm){
        waitForLoadingScreen();
        findElement(searchBox).clear();
        findElement(searchBox).sendKeys(searchTerm);
        pause(2000);
        return this;
    }

    public boolean briefingBooksAreDisplayed(){
        waitForLoadingScreen();
        return !findElement(reportList).getText().contains("No briefing books available.");
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
}
