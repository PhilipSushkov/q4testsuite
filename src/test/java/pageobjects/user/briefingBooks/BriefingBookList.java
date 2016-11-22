package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

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
}
