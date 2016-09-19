package pageobjects.user.reportBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-13.
 */
public class ReportDetailsPage extends AbstractPageObject{
    private final By generateButton = By.xpath("//*[contains(text(), 'Generate')]");
    private final By editButton = By.xpath("//*[contains(text(), 'Edit')]");
    private final By deleteButton = By.cssSelector(".report-edit-footer .delete-action .delete-button");
    private final By deleteConfirmation = By.xpath("//*[contains(text(), 'Delete')]");
    private final By yesButton = By.xpath("//*[contains(text(), 'Yes')]");
    private final By addMoreButton = By.xpath("//*[contains(text(), 'Add more')]");
    private final By tagInputField = By.cssSelector(".add-report-entity .x-input-el");
    private final By addButton = By.cssSelector(".add-report-entity .continue-button");
    private final By reportContent = By.cssSelector(".x-list .x-list-inner");
    private final By saveButton = By.cssSelector(".q4-hero-banner .action-button + .action-button");

    public ReportDetailsPage(WebDriver driver) {
        super(driver);
    }
    public ReportDetailsPage generateTearSheet() {
        wait.until(ExpectedConditions.elementToBeClickable(generateButton)).click();

        return this;
    }

    public ReportDetailsPage editTearSheet() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        findElement(editButton).click();

        return this;
    }

    public ReportDetailsPage deleteTearSheet() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        findElement(deleteButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteConfirmation));
        findElement(deleteConfirmation).click();
        wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        findElement(yesButton).click();

        return this;
    }

    public ReportDetailsPage addMoreTags(String newTag) {
        findElement(addMoreButton).click();
        findElement(tagInputField).sendKeys(newTag);
        findElement(tagInputField).sendKeys(Keys.RETURN);
        findElement(addButton).click();

        return this;
    }

    public String getContent() {
        pause(500L);
        return findElement(reportContent).getText();
    }

    public ReportDetailsPage saveChanges() {
        pause(500L);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        findElement(saveButton).click();

        return this;
    }
}
