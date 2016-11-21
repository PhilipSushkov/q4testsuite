package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-13.
 */
public class BriefingBookDetailsPage extends AbstractPageObject{
    private final By deleteButton = By.cssSelector(".x-button-icon x-shown q4i-trashbin-4pt");
    private final By deleteConfirmation = By.xpath("//*[contains(text(), 'Yes')]");
    private final By saveButton = By.cssSelector(".q4-hero-banner .action-button + .action-button");

    public BriefingBookDetailsPage(WebDriver driver) {
        super(driver);
    }

    public BriefingBookList deleteBriefingBookFromDetailsPage() {
        findElement(deleteButton).click();
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
}
