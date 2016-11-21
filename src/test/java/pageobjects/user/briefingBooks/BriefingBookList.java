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
}
